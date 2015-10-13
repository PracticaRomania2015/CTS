USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetPersonalTickets]    Script Date: 10/8/2015 1:19:45 PM ******/
DROP PROCEDURE [dbo].[GetPersonalTickets]
GO

/****** Object:  StoredProcedure [dbo].[GetPersonalTickets]    Script Date: 10/8/2015 1:19:45 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetPersonalTickets]
	@UserId INT,
	@RequestedPageNumber INT,
	@TicketsPerPage INT,
	@TextToSearch varchar(50),
	@SearchType varchar(50),
	@SelectedCategoryID INT,
	@SelectedPriorityID INT,
	@SelectedStatusID INT,
	@SortType varchar(50),
	@IsSearchASC bit,
	@TotalNumberOfPages INT OUTPUT

AS
BEGIN

	-- Get MY tickets --

	-- Declaring the variables --
	DECLARE @TotalNumberOfTickets INT
	DECLARE @FirstTicketOnPage INT
	DECLARE @LastTicketOnPage INT

	-- Creating a temp table with ticket info --
	select [Ticket].TicketId, [Ticket].Subject, [Category].CategoryName, [State].StateName, [Priority].PriorityName, [User].FirstName, [User].UserId
	into #TempTicketBaseInfo
	from [Ticket]
		INNER JOIN [State] ON [Ticket].StateId = [State].StateId
		INNER JOIN [Category] ON [Ticket].CategoryId = [Category].CategoryId
		LEFT JOIN [User] ON [User].UserId = [Ticket].AssignedToUserId
		INNER JOIN [Priority] ON [Ticket].PriorityId = [Priority].PriorityId
	where (@SelectedCategoryID = [Ticket].CategoryId or @SelectedCategoryID = 0 or @SelectedCategoryID = [Category].ParentCategoryId) AND 
		  (@SelectedPriorityID = [Ticket].PriorityId or @SelectedPriorityID = 0) AND 
		  (@SelectedStatusID = [Ticket].StateId or @SelectedStatusID = 0)
	
	-- Creating a temp table with comment info for each ticket --
	select * 
	into #TempTicketCommentInfo
	from (
		select [TicketComment].TicketId, MIN([TicketComment].DateTime) AS SubmitDate, MAX([TicketComment].DateTime) AS AnswerDate,
		CAST((select top(1) GetSubmitter.UserId from [TicketComment] as GetSubmitter where GetSubmitter.TicketId = [TicketComment].TicketId) as int) AS TicketOwnerId 
		from [TicketComment]
		group by [TicketComment].TicketId
	) as SubmitAndLastDate
	where SubmitAndLastDate.TicketOwnerId = @UserId
	
	-- Creating a temp table that merges the data from the previous 2 tables --
	select #TempTicketBaseInfo.TicketId, #TempTicketCommentInfo.TicketOwnerId, #TempTicketBaseInfo.Subject, #TempTicketBaseInfo.CategoryName, #TempTicketBaseInfo.StateName, #TempTicketBaseInfo.PriorityName, #TempTicketBaseInfo.FirstName as AssignedUserName, #TempTicketBaseInfo.UserId as AssignedUserId, #TempTicketCommentInfo.SubmitDate, #TempTicketCommentInfo.AnswerDate
	into #TempAllTicketInfo
	from #TempTicketBaseInfo
		right outer join #TempTicketCommentInfo on #TempTicketBaseInfo.TicketId = #TempTicketCommentInfo.TicketId
	where #TempTicketBaseInfo.TicketId is not null

	-- Prepping for ticket search --
	declare @sql varchar(max) = ''
	declare @IsSearchASCorDESC varchar(10) = ''

	select @IsSearchASCorDESC = CASE	
									WHEN @IsSearchASC = 0 THEN 'DESC'
									WHEN @IsSearchASC = 1 THEN 'ASC'
									ELSE 'DESC'
								END

	-- Searching the tickets for the desired info --
	select *
	into #TempSearchResults
	from #TempAllTicketInfo 
	where (len(@TextToSearch) = 0) OR 
	(@SearchType = 'Subject' and #TempAllTicketInfo.Subject like ('%' + @TextToSearch + '%')) or
	(@SearchType = 'TicketId' and #TempAllTicketInfo.TicketId like ('%' + @TextToSearch + '%'))

	-- Setting the total number of tickets --
	select @TotalNumberOfTickets=count(1) 
	from #TempSearchResults

	set @TotalNumberOfPages = @TotalNumberOfTickets / @TicketsPerPage + 1 + POWER(0,@TotalNumberOfTickets) - POWER(0,@TotalNumberOfTickets % @TicketsPerPage);
	set @FirstTicketOnPage = (@RequestedPageNumber - 1 ) * @TicketsPerPage + 1;
	set @LastTicketOnPage = @RequestedPageNumber * @TicketsPerPage;

	-- Getting requested ticket page --
	SET @sql = '
		SELECT Final.TicketId, Final.Subject, Final.CategoryName, Final.StateName, Final.PriorityName, Final.AssignedUserName, Final.SubmitDate, Final.AnswerDate
		FROM (
			SELECT	ROW_NUMBER() OVER(ORDER BY t.' + @SortType + ' ' + @IsSearchASCorDESC + ') AS RowNumber,
					t.TicketId, 
					t.Subject, 
					t.CategoryName, 
					t.StateName, 
					t.PriorityName, 
					t.AssignedUserName, 
					t.SubmitDate, 
					t.AnswerDate
	
			FROM #TempSearchResults t) AS Final
		WHERE CAST(Final.RowNumber AS INT) BETWEEN ' + CAST(@FirstTicketOnPage AS VARCHAR) + ' AND ' + CAST(@LastTicketOnPage AS VARCHAR)
	
	EXECUTE(@sql)

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @DateTime = SYSDATETIME()
	SELECT @Action = 'Requested personal tickets; the stored procedure was successfully executedwith the params: ' + 
	cast(@UserId as varchar) + '_' + cast(@RequestedPageNumber as varchar) + '_' + cast(@TicketsPerPage as varchar) + '_' + @TextToSearch + '_' + @SearchType + '_' +
	cast(@SelectedCategoryID as varchar) + '_' + cast(@SelectedPriorityID as varchar) + '_' + cast(@SelectedStatusID as varchar) + '_' + @SortType + '_' + 
	cast(@IsSearchASC as varchar) + '_' + cast(@TotalNumberOfPages as varchar) + '.'

	EXEC dbo.AddAuditEvent 
	@UserId = @UserId,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END
GO
