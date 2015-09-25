USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetTickets]    Script Date: 9/25/2015 10:11:45 AM ******/
DROP PROCEDURE [dbo].[GetTickets]
GO

/****** Object:  StoredProcedure [dbo].[GetTickets]    Script Date: 9/25/2015 10:11:45 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetTickets]
	@UserId INT,
	@TypeOfRequest INT,
	@RequestedPageNumber INT,
	@TicketsPerPage INT,
	@TextToSearch varchar(50),
	@SearchType varchar(50),
	@SortType varchar(50),
	@IsSearchASC bit,
	@TotalNumberOfPages INT OUTPUT

AS
BEGIN
	
	DECLARE @TotalNumberOfTickets INT
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SET @TotalNumberOfTickets = 0
	SELECT @DateTime = SYSDATETIME()

	IF @TypeOfRequest = 0
	BEGIN
		-- personal tickets
		SELECT @TotalNumberOfTickets = COUNT(Ticket.TicketId)
		FROM Ticket
			INNER JOIN TicketComment ON Ticket.TicketId = TicketComment.TicketId
			INNER JOIN State ON Ticket.StateId = State.StateId
			INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId
			LEFT JOIN [User] ON [User].UserId = Ticket.AssignedToUserId
			INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId
		WHERE (@TextToSearch = '' AND TicketComment.UserId = @UserId AND (Ticket.AssignedToUserId != @UserId OR Ticket.AssignedToUserId IS NULL))
			OR (@SearchType = 'Subject' AND Subject like ('%' + @TextToSearch + '%') AND TicketComment.UserId = @UserId AND (Ticket.AssignedToUserId != @UserId OR Ticket.AssignedToUserId IS NULL))
			OR (@SearchType = 'Category' AND CategoryName like ('%' + @TextToSearch + '%') AND TicketComment.UserId = @UserId AND (Ticket.AssignedToUserId != @UserId OR Ticket.AssignedToUserId IS NULL))
			OR (@SearchType = 'Status' AND StateName like ('%' + @TextToSearch + '%') AND TicketComment.UserId = @UserId AND (Ticket.AssignedToUserId != @UserId OR Ticket.AssignedToUserId IS NULL)) 
			OR (@SearchType = 'TicketId' AND Ticket.TicketId like ('%' + @TextToSearch + '%') AND TicketComment.UserId = @UserId AND (Ticket.AssignedToUserId != @UserId OR Ticket.AssignedToUserId IS NULL))

		IF @TotalNumberOfTickets % @TicketsPerPage =0
		BEGIN
			SELECT @TotalNumberOfPages = @TotalNumberOfTickets/@TicketsPerPage 
		END
		ELSE
		BEGIN
			SELECT @TotalNumberOfPages = @TotalNumberOfTickets/@TicketsPerPage +1
		END

		IF @TotalNumberOfPages = 0
		BEGIN
			SET @TotalNumberOfPages=1
		END;

		WITH Tickets AS
		(
			SELECT Ticket.TicketId, Subject, MIN(TicketComment.DateTime) AS DateTime, CategoryName, StateName, FirstName, LastName, MAX(TicketComment.DateTime) AS LastDateTime, PriorityName,
			ROW_NUMBER() OVER
			(
				ORDER BY 
					CASE WHEN @SortType = '' THEN MIN(TicketComment.DateTime) END DESC,
					CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 1 THEN Ticket.TicketId END ASC,
					CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 1 THEN Subject END ASC,
					CASE WHEN @SortType = 'Category' AND @IsSearchASC = 1 THEN CategoryName END ASC,
					CASE WHEN @SortType = 'Status' AND @IsSearchASC = 1 THEN StateName END ASC,
					CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 1 THEN MIN(TicketComment.DateTime) END ASC,
					CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 1 THEN MAX(TicketComment.DateTime) END ASC,
					CASE WHEN @SortType = 'Priority' AND @IsSearchASC = 1 THEN PriorityName END ASC,
					CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 0 THEN Ticket.TicketId END DESC,
					CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 0 THEN Subject END DESC,
					CASE WHEN @SortType = 'Category' AND @IsSearchASC = 0 THEN CategoryName END DESC,
					CASE WHEN @SortType = 'Status' AND @IsSearchASC = 0 THEN StateName END DESC,
					CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 0 THEN MIN(TicketComment.DateTime) END DESC,
					CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 0 THEN MAX(TicketComment.DateTime) END DESC,
					CASE WHEN @SortType = 'Priority' AND @IsSearchASC = 0 THEN PriorityName END DESC
			) AS RowNumber
			FROM Ticket
				INNER JOIN TicketComment ON Ticket.TicketId = TicketComment.TicketId
				INNER JOIN State ON Ticket.StateId = State.StateId
				INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId
				LEFT JOIN [User] ON [User].UserId = Ticket.AssignedToUserId
				INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId
			WHERE (@TextToSearch = '' AND TicketComment.UserId = @UserId AND (Ticket.AssignedToUserId != @UserId OR Ticket.AssignedToUserId IS NULL))
				OR (@SearchType = 'Subject' AND Subject like ('%' + @TextToSearch + '%') AND TicketComment.UserId = @UserId AND (Ticket.AssignedToUserId != @UserId OR Ticket.AssignedToUserId IS NULL))
				OR (@SearchType = 'Category' AND CategoryName like ('%' + @TextToSearch + '%') AND TicketComment.UserId = @UserId AND (Ticket.AssignedToUserId != @UserId OR Ticket.AssignedToUserId IS NULL))
				OR (@SearchType = 'Status' AND StateName like ('%' + @TextToSearch + '%') AND TicketComment.UserId = @UserId AND (Ticket.AssignedToUserId != @UserId OR Ticket.AssignedToUserId IS NULL))
				OR (@SearchType = 'TicketId' AND Ticket.TicketId like ('%' + @TextToSearch + '%') AND TicketComment.UserId = @UserId AND (Ticket.AssignedToUserId != @UserId OR Ticket.AssignedToUserId IS NULL))
			GROUP BY Ticket.TicketId, Subject, CategoryName, StateName, FirstName, LastName, PriorityName
		)
		SELECT TicketId, Subject, DateTime, CategoryName, StateName, FirstName, LastName, LastDateTime, PriorityName
		FROM Tickets
		WHERE RowNumber BETWEEN ((@RequestedPageNumber-1)*@TicketsPerPage+1) AND (@RequestedPageNumber*@TicketsPerPage)

		-- set param for audit event
		SELECT @Action = 'Requested personal tickets; the stored procedure was successfully executed.'
	END
	ELSE
	BEGIN
		-- tickets that I manage
		SELECT @TotalNumberOfTickets = COUNT(Ticket.TicketId)
		FROM Ticket
			INNER JOIN State ON Ticket.StateId = State.StateId
			INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId
			INNER JOIN UserCategory ON UserCategory.UserId = @UserId AND (UserCategory.CategoryId = Ticket.CategoryId OR UserCategory.CategoryId = Category.ParentCategoryId)
			LEFT JOIN [User] ON [User].UserId = Ticket.AssignedToUserId
			INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId
		WHERE (@TextToSearch = '')
			OR (@SearchType = 'Subject' AND Subject like ('%' + @TextToSearch + '%'))
			OR (@SearchType = 'Category' AND CategoryName like ('%' + @TextToSearch + '%'))
			OR (@SearchType = 'Status' AND StateName like ('%' + @TextToSearch + '%')) 
			OR (@SearchType = 'TicketId' AND Ticket.TicketId like ('%' + @TextToSearch + '%')) 

		IF @TotalNumberOfTickets % @TicketsPerPage =0
		BEGIN
			SELECT @TotalNumberOfPages = @TotalNumberOfTickets/@TicketsPerPage 
		END
		ELSE
		BEGIN
			SELECT @TotalNumberOfPages = @TotalNumberOfTickets/@TicketsPerPage +1
		END

		IF @TotalNumberOfPages = 0
		BEGIN
			SET @TotalNumberOfPages=1
		END;

		WITH Tickets AS
		(
			SELECT Ticket.TicketId, Subject, MIN(TicketComment.DateTime) AS DateTime, CategoryName, StateName, FirstName, LastName, MAX(TicketComment.DateTime) AS LastDateTime, PriorityName,
			ROW_NUMBER() OVER
			(
				ORDER BY 
					CASE WHEN @SortType = '' THEN MIN(TicketComment.DateTime) END DESC,
					CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 1 THEN Ticket.TicketId END ASC,
					CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 1 THEN Subject END ASC,
					CASE WHEN @SortType = 'Category' AND @IsSearchASC = 1 THEN CategoryName END ASC,
					CASE WHEN @SortType = 'Status' AND @IsSearchASC = 1 THEN StateName END ASC,
					CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 1 THEN MIN(TicketComment.DateTime) END ASC,
					CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 1 THEN MAX(TicketComment.DateTime) END ASC,
					CASE WHEN @SortType = 'Priority' AND @IsSearchASC = 1 THEN PriorityName END ASC,
					CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 0 THEN Ticket.TicketId END DESC,
					CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 0 THEN Subject END DESC,
					CASE WHEN @SortType = 'Category' AND @IsSearchASC = 0 THEN CategoryName END DESC,
					CASE WHEN @SortType = 'Status' AND @IsSearchASC = 0 THEN StateName END DESC,
					CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 0 THEN MIN(TicketComment.DateTime) END DESC,
					CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 0 THEN MAX(TicketComment.DateTime) END DESC,
					CASE WHEN @SortType = 'Priority' AND @IsSearchASC = 0 THEN PriorityName END DESC
			) AS RowNumber
			FROM Ticket
				INNER JOIN TicketComment ON Ticket.TicketId = TicketComment.TicketId
				INNER JOIN State ON Ticket.StateId = State.StateId
				INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId
				LEFT JOIN [User] ON [User].UserId = Ticket.AssignedToUserId
				INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId
			WHERE (@TextToSearch = '') AND Ticket.CategoryId IN (select Category.CategoryId from UserCategory inner join Category on Category.CategoryId = UserCategory.CategoryId or Category.ParentCategoryId = UserCategory.CategoryId where UserCategory.UserId = @UserId)
				OR (@SearchType = 'Subject' AND Subject like ('%' + @TextToSearch + '%')) AND Ticket.CategoryId IN (select Category.CategoryId from UserCategory inner join Category on Category.CategoryId = UserCategory.CategoryId or Category.ParentCategoryId = UserCategory.CategoryId where UserCategory.UserId = @UserId)
				OR (@SearchType = 'Category' AND CategoryName like ('%' + @TextToSearch + '%')) AND Ticket.CategoryId IN (select Category.CategoryId from UserCategory inner join Category on Category.CategoryId = UserCategory.CategoryId or Category.ParentCategoryId = UserCategory.CategoryId where UserCategory.UserId = @UserId)
				OR (@SearchType = 'Status' AND StateName like ('%' + @TextToSearch + '%'))  AND Ticket.CategoryId IN (select Category.CategoryId from UserCategory inner join Category on Category.CategoryId = UserCategory.CategoryId or Category.ParentCategoryId = UserCategory.CategoryId where UserCategory.UserId = @UserId)
				OR (@SearchType = 'TicketId' AND Ticket.TicketId like ('%' + @TextToSearch + '%'))  AND Ticket.CategoryId IN (select Category.CategoryId from UserCategory inner join Category on Category.CategoryId = UserCategory.CategoryId or Category.ParentCategoryId = UserCategory.CategoryId where UserCategory.UserId = @UserId)
			GROUP BY Ticket.TicketId, Subject, CategoryName, StateName, FirstName, LastName, PriorityName
		)
		SELECT TicketId, Subject, DateTime, CategoryName, StateName, FirstName, LastName, LastDateTime, PriorityName
		FROM Tickets
		WHERE RowNumber BETWEEN ((@RequestedPageNumber-1)*@TicketsPerPage+1) AND (@RequestedPageNumber*@TicketsPerPage)

		-- set param for audit event
		SELECT @Action = 'Requested assigned tickets; the stored procedure was successfully executed.'
	END

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = @UserId,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END
GO

