USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetPersonalTickets]    Script Date: 10/19/2015 3:20:50 PM ******/
DROP PROCEDURE [dbo].[GetPersonalTickets]
GO

/****** Object:  StoredProcedure [dbo].[GetPersonalTickets]    Script Date: 10/19/2015 3:20:50 PM ******/
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

	-- Cancel parameter sniffing by making the variables local --

	DECLARE @local_UserId INT;
	SET @local_UserId = @UserId;

	DECLARE @local_RequestedPageNumber INT;
	SET @local_RequestedPageNumber = @RequestedPageNumber;

	DECLARE @local_TicketsPerPage INT;
	SET @local_TicketsPerPage = @TicketsPerPage;

	DECLARE @local_TextToSearch varchar(50);
	SET @local_TextToSearch = @TextToSearch;

	DECLARE @local_SearchType varchar(50);
	SET @local_SearchType = @SearchType;

	DECLARE @local_SelectedCategoryID INT;
	SET @local_SelectedCategoryID = @SelectedCategoryID;

	DECLARE @local_SelectedPriorityID INT;
	SET @local_SelectedPriorityID = @SelectedPriorityID;

	DECLARE @local_SelectedStatusID INT;
	SET @local_SelectedStatusID = @SelectedStatusID;

	DECLARE @local_SortType varchar(50);
	SET @local_SortType = @SortType;

	DECLARE @local_IsSearchASC bit;
	SET @local_IsSearchASC = @IsSearchASC;

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
	where (@local_SelectedCategoryID = [Ticket].CategoryId or @local_SelectedCategoryID = 0 or @local_SelectedCategoryID = [Category].ParentCategoryId) AND 
		  (@local_SelectedPriorityID = [Ticket].PriorityId or @local_SelectedPriorityID = 0) AND 
		  (@local_SelectedStatusID = [Ticket].StateId or @local_SelectedStatusID = 0)
	
	-- Creating a temp table with comment info for each ticket --
	select * 
	into #TempTicketCommentInfo
	from (
		select [TicketComment].TicketId, MIN([TicketComment].DateTime) AS SubmitDate, MAX([TicketComment].DateTime) AS AnswerDate,
		CAST((select top(1) GetSubmitter.UserId from [TicketComment] as GetSubmitter where GetSubmitter.TicketId = [TicketComment].TicketId) as int) AS TicketOwnerId 
		from [TicketComment]
		group by [TicketComment].TicketId
	) as SubmitAndLastDate
	where SubmitAndLastDate.TicketOwnerId = @local_UserId
	
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
									WHEN @local_IsSearchASC = 0 THEN 'DESC'
									WHEN @local_IsSearchASC = 1 THEN 'ASC'
									ELSE 'DESC'
								END

	-- Searching the tickets for the desired info --
	select *
	into #TempSearchResults
	from #TempAllTicketInfo 
	where (len(@local_TextToSearch) = 0) OR 
	(@local_SearchType = 'Subject' and #TempAllTicketInfo.Subject like ('%' + @local_TextToSearch + '%')) or
	(@local_SearchType = 'TicketId' and #TempAllTicketInfo.TicketId like ('%' + @local_TextToSearch + '%'))

	-- Setting the total number of tickets --
	select @TotalNumberOfTickets=count(1) 
	from #TempSearchResults

	set @TotalNumberOfPages = @TotalNumberOfTickets / @local_TicketsPerPage + 1 + POWER(0,@TotalNumberOfTickets) - POWER(0,@TotalNumberOfTickets % @local_TicketsPerPage);
	set @FirstTicketOnPage = (@local_RequestedPageNumber - 1 ) * @local_TicketsPerPage + 1;
	set @LastTicketOnPage = @local_RequestedPageNumber * @local_TicketsPerPage;

	-- Getting requested ticket page --
	SET @sql = '
		SELECT Final.TicketId, Final.Subject, Final.CategoryName, Final.StateName, Final.PriorityName, Final.AssignedUserName, Final.SubmitDate, Final.AnswerDate
		FROM (
			SELECT	ROW_NUMBER() OVER(ORDER BY t.' + @local_SortType + ' ' + @IsSearchASCorDESC + ') AS RowNumber,
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
	cast(@local_UserId as varchar) + '_' + cast(@local_RequestedPageNumber as varchar) + '_' + cast(@local_TicketsPerPage as varchar) + '_' + @local_TextToSearch + '_' + @local_SearchType + '_' +
	cast(@local_SelectedCategoryID as varchar) + '_' + cast(@local_SelectedPriorityID as varchar) + '_' + cast(@local_SelectedStatusID as varchar) + '_' + @local_SortType + '_' + 
	cast(@local_IsSearchASC as varchar) + '_' + cast(@TotalNumberOfPages as varchar) + '.'

	EXEC dbo.AddAuditEvent 
	@UserId = @local_UserId,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END
GO

