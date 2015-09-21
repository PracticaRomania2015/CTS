USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetTickets]    Script Date: 9/21/2015 12:53:29 PM ******/
DROP PROCEDURE [dbo].[GetTickets]
GO

/****** Object:  StoredProcedure [dbo].[GetTickets]    Script Date: 9/21/2015 12:53:29 PM ******/
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
	DECLARE @TicketTo INT
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SET @TotalNumberOfTickets = 0
	SELECT @DateTime = SYSDATETIME()

	IF @TypeOfRequest = 0
	BEGIN
		SELECT @TotalNumberOfTickets = COUNT(*) FROM
		(
			SELECT Ticket.TicketId, MAX(Ticket.Subject) AS Subject, MIN(TicketComment.DateTime) AS DateTime, MAX(Category.CategoryName) AS CategoryName, MAX(State.StateName) AS StateName, MAX(FirstName) AS FirstName, MAX(LastName) AS LastName, MAX(TicketComment.DateTime) AS LastDateTime, MAX(Priority.PriorityName) as PriorityName
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
			GROUP BY Ticket.TicketId
		)CountId

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
		END

		SELECT @TicketTo = @RequestedPageNumber * @TicketsPerPage

		IF (@TicketTo > @TotalNumberOfTickets)
		BEGIN
			SELECT @TicketsPerPage = @TicketsPerPage - (@TicketTo - @TotalNumberOfTickets)
		END

		SELECT * FROM (SELECT TOP (@TicketsPerPage) * FROM (SELECT TOP (@TicketTo) Ticket.TicketId AS TicketId, MAX(Ticket.Subject) AS Subject, MIN(TicketComment.DateTime) AS DateTime, MAX(Category.CategoryName) AS CategoryName, MAX(State.StateName) AS StateName, MAX(FirstName) AS FirstName, MAX(LastName) AS LastName, MAX(TicketComment.DateTime) AS LastDateTime, MAX(Priority.PriorityName) as PriorityName
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
		GROUP BY Ticket.TicketId
		ORDER BY 
			CASE WHEN @SortType = '' THEN MIN(TicketComment.DateTime) END DESC,
			CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 1 THEN Ticket.TicketId END ASC,
			CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 1 THEN MAX(Subject) END ASC,
			CASE WHEN @SortType = 'Category' AND @IsSearchASC = 1 THEN MAX(CategoryName) END ASC,
			CASE WHEN @SortType = 'Status' AND @IsSearchASC = 1 THEN MAX(StateName) END ASC,
			CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 1 THEN MIN(DateTime) END ASC,
			CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 1 THEN MAX(DateTime) END ASC,
			CASE WHEN @SortType = 'Priority' AND @IsSearchASC = 1 THEN MAX(PriorityName) END ASC,
			CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 0 THEN Ticket.TicketId END DESC,
			CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 0 THEN MAX(Subject) END DESC,
			CASE WHEN @SortType = 'Category' AND @IsSearchASC = 0 THEN MAX(CategoryName) END DESC,
			CASE WHEN @SortType = 'Status' AND @IsSearchASC = 0 THEN MAX(StateName) END DESC,
			CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 0 THEN MIN(DateTime) END DESC,
			CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 0 THEN MAX(DateTime) END DESC,
			CASE WHEN @SortType = 'Priority' AND @IsSearchASC = 0 THEN MAX(PriorityName) END DESC
		) t GROUP BY t.TicketId, t.Subject, t.DateTime, t.CategoryName, t.StateName, t.FirstName, t.LastName, t.LastDateTime, t.PriorityName
			ORDER BY
				CASE WHEN @SortType = '' THEN MIN(t.DateTime) END ASC,
				CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 1 THEN t.TicketId END DESC,
				CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 1 THEN t.Subject END DESC,
				CASE WHEN @SortType = 'Category' AND @IsSearchASC = 1 THEN t.CategoryName END DESC,
				CASE WHEN @SortType = 'Status' AND @IsSearchASC = 1 THEN t.StateName END DESC,
				CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 1 THEN t.DateTime END DESC,
				CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 1 THEN t.LastDateTime END DESC,
				CASE WHEN @SortType = 'Priority' AND @IsSearchASC = 1 THEN PriorityName END DESC,
				CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 0 THEN t.TicketId END ASC,
				CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 0 THEN t.Subject END ASC,
				CASE WHEN @SortType = 'Category' AND @IsSearchASC = 0 THEN t.CategoryName END ASC,
				CASE WHEN @SortType = 'Status' AND @IsSearchASC = 0 THEN t.StateName END ASC,
				CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 0 THEN t.DateTime END ASC,
				CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 0 THEN t.LastDateTime END ASC,
				CASE WHEN @SortType = 'Priority' AND @IsSearchASC = 0 THEN PriorityName END ASC
				) tt GROUP BY tt.TicketId, tt.Subject, tt.DateTime, tt.CategoryName, tt.StateName, tt.FirstName, tt.LastName, tt.LastDateTime, tt.PriorityName
					 ORDER BY
						CASE WHEN @SortType = '' THEN MIN(tt.DateTime) END DESC,
						CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 1 THEN tt.TicketId END ASC,
						CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 1 THEN tt.Subject END ASC,
						CASE WHEN @SortType = 'Category' AND @IsSearchASC = 1 THEN tt.CategoryName END ASC,
						CASE WHEN @SortType = 'Status' AND @IsSearchASC = 1 THEN tt.StateName END ASC,
						CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 1 THEN tt.DateTime END ASC,
						CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 1 THEN tt.LastDateTime END ASC,
						CASE WHEN @SortType = 'Priority' AND @IsSearchASC = 1 THEN PriorityName END ASC,
						CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 0 THEN tt.TicketId END DESC,
						CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 0 THEN tt.Subject END DESC,
						CASE WHEN @SortType = 'Category' AND @IsSearchASC = 0 THEN tt.CategoryName END DESC,
						CASE WHEN @SortType = 'Status' AND @IsSearchASC = 0 THEN tt.StateName END DESC,
						CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 0 THEN tt.DateTime END DESC,
						CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 0 THEN tt.LastDateTime END DESC,
						CASE WHEN @SortType = 'Priority' AND @IsSearchASC = 0 THEN PriorityName END DESC

		-- set param for audit event
		SELECT @Action = 'Requested personal tickets; the stored procedure was successfully executed.'
	END
	ELSE
	BEGIN
		SELECT @TotalNumberOfTickets = COUNT(*) FROM
		(
			SELECT TicketComment.TicketId, MAX(Ticket.Subject) AS Subject, MIN(TicketComment.DateTime) AS DateTime, MAX(Category.CategoryName) AS CategoryName, MAX(State.StateName) AS StateName, MAX(FirstName) AS FirstName, MAX(LastName) AS LastName, MAX(TicketComment.DateTime) AS LastDateTime, MAX(Priority.PriorityName) as PriorityName
			FROM Ticket
			INNER JOIN TicketComment ON Ticket.TicketId = TicketComment.TicketId
			INNER JOIN State ON Ticket.StateId = State.StateId
			INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId
			INNER JOIN UserCategory ON UserCategory.UserId = @UserId AND (UserCategory.CategoryId = Ticket.CategoryId OR UserCategory.CategoryId = Category.ParentCategoryId)
			LEFT JOIN [User] ON [User].UserId = Ticket.AssignedToUserId
			INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId
			WHERE (@TextToSearch = '')
				OR (@SearchType = 'Subject' AND Subject like ('%' + @TextToSearch + '%'))
				OR (@SearchType = 'Category' AND CategoryName like ('%' + @TextToSearch + '%'))
				OR (@SearchType = 'Status' AND StateName like ('%' + @TextToSearch + '%')) 
			GROUP BY TicketComment.TicketId	
		)CountId;

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
		END

		SELECT @TicketTo = @RequestedPageNumber * @TicketsPerPage

		IF (@TicketTo > @TotalNumberOfTickets)
		BEGIN
			SELECT @TicketsPerPage = @TicketsPerPage - (@TicketTo - @TotalNumberOfTickets)
		END

		SELECT * FROM (SELECT TOP (@TicketsPerPage) * FROM (SELECT TOP (@TicketTo) Ticket.TicketId, MAX(Ticket.Subject) AS Subject, MIN(TicketComment.DateTime) AS DateTime, MAX(Category.CategoryName) AS CategoryName, MAX(State.StateName) AS StateName, MAX(FirstName) AS FirstName, MAX(LastName) AS LastName, MAX(TicketComment.DateTime) AS LastDateTime, MAX(Priority.PriorityName) as PriorityName
		FROM Ticket
		INNER JOIN TicketComment ON Ticket.TicketId = TicketComment.TicketId
		INNER JOIN State ON Ticket.StateId = State.StateId
		INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId
		INNER JOIN UserCategory ON UserCategory.UserId = @UserId AND (UserCategory.CategoryId = Ticket.CategoryId OR UserCategory.CategoryId = Category.ParentCategoryId)
		LEFT JOIN [User] ON [User].UserId = Ticket.AssignedToUserId
		INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId
		WHERE (@TextToSearch = '')
			OR (@SearchType = 'Subject' AND Subject like ('%' + @TextToSearch + '%'))
			OR (@SearchType = 'Category' AND CategoryName like ('%' + @TextToSearch + '%'))
			OR (@SearchType = 'Status' AND StateName like ('%' + @TextToSearch + '%')) 
		GROUP BY Ticket.TicketId
		ORDER BY 
			CASE WHEN @SortType = '' THEN MIN(TicketComment.DateTime) END DESC,
			CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 1 THEN Ticket.TicketId END ASC,
			CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 1 THEN MAX(Subject) END ASC,
			CASE WHEN @SortType = 'Category' AND @IsSearchASC = 1 THEN MAX(CategoryName) END ASC,
			CASE WHEN @SortType = 'Status' AND @IsSearchASC = 1 THEN MAX(StateName) END ASC,
			CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 1 THEN MIN(DateTime) END ASC,
			CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 1 THEN  MAX(DateTime) END ASC,
			CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 0 THEN Ticket.TicketId END DESC,
			CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 0 THEN MAX(Subject) END DESC,
			CASE WHEN @SortType = 'Category' AND @IsSearchASC = 0 THEN MAX(CategoryName) END DESC,
			CASE WHEN @SortType = 'Status' AND @IsSearchASC = 0 THEN MAX(StateName) END DESC,
			CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 0 THEN MIN(DateTime) END DESC,
			CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 0 THEN MAX(DateTime) END DESC
		) t GROUP BY t.TicketId, t.Subject, t.DateTime, t.CategoryName, t.StateName, t.FirstName, t.LastName, t.LastDateTime, t.PriorityName
			ORDER BY
				CASE WHEN @SortType = '' THEN MIN(t.DateTime) END ASC,
				CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 1 THEN t.TicketId END DESC,
				CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 1 THEN t.Subject END DESC,
				CASE WHEN @SortType = 'Category' AND @IsSearchASC = 1 THEN t.CategoryName END DESC,
				CASE WHEN @SortType = 'Status' AND @IsSearchASC = 1 THEN t.StateName END DESC,
				CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 1 THEN t.DateTime END DESC,
				CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 1 THEN t.LastDateTime END DESC,
				CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 0 THEN t.TicketId END ASC,
				CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 0 THEN t.Subject END ASC,
				CASE WHEN @SortType = 'Category' AND @IsSearchASC = 0 THEN t.CategoryName END ASC,
				CASE WHEN @SortType = 'Status' AND @IsSearchASC = 0 THEN t.StateName END ASC,
				CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 0 THEN t.DateTime END ASC,
				CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 0 THEN t.LastDateTime END ASC
				) tt GROUP BY tt.TicketId, tt.Subject, tt.DateTime, tt.CategoryName, tt.StateName, tt.FirstName, tt.LastName, tt.LastDateTime, tt.PriorityName
					 ORDER BY
						CASE WHEN @SortType = '' THEN MIN(tt.DateTime) END DESC,
						CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 1 THEN tt.TicketId END ASC,
						CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 1 THEN tt.Subject END ASC,
						CASE WHEN @SortType = 'Category' AND @IsSearchASC = 1 THEN tt.CategoryName END ASC,
						CASE WHEN @SortType = 'Status' AND @IsSearchASC = 1 THEN tt.StateName END ASC,
						CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 1 THEN tt.DateTime END ASC,
						CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 1 THEN tt.LastDateTime END ASC,
						CASE WHEN @SortType = 'TicketId' AND @IsSearchASC = 0 THEN tt.TicketId END DESC,
						CASE WHEN @SortType = 'Subject' AND @IsSearchASC = 0 THEN tt.Subject END DESC,
						CASE WHEN @SortType = 'Category' AND @IsSearchASC = 0 THEN tt.CategoryName END DESC,
						CASE WHEN @SortType = 'Status' AND @IsSearchASC = 0 THEN tt.StateName END DESC,
						CASE WHEN @SortType = 'SubmitDate' AND @IsSearchASC = 0 THEN tt.DateTime END ASC,
						CASE WHEN @SortType = 'LastDateTime' AND @IsSearchASC = 0 THEN tt.LastDateTime END DESC

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

