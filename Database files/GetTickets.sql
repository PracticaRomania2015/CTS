USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetTickets]    Script Date: 9/10/2015 10:09:26 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetTickets]
	@UserId INT,
	@IsViewMyTicketsRequest INT,
	@RequestedPageNumber INT,
	@TicketsPerPage INT,
	@TextToSearch varchar(50),
	@SearchType varchar(50),
	@TotalNumberOfPages INT OUTPUT

AS
BEGIN
	
	DECLARE @TotalNumberOfTickets INT
	DECLARE @TicketTo INT
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	IF @IsViewMyTicketsRequest = 0
	BEGIN

		SET @TotalNumberOfTickets =0

		SELECT @TotalNumberOfTickets = COUNT(*) FROM
		(
			SELECT Ticket.TicketId, MAX(Ticket.Subject) AS Subject, MIN(TicketComment.DateTime) AS DateTime, MAX(Category.CategoryId) AS CategoryId, MAX(Category.CategoryName) AS CategoryName, MAX(State.StateId) AS StateId, MAX(State.StateName) AS StateName, MAX(FirstName) AS FirstName, MAX(LastName) AS LastName, MAX(Ticket.AssignedToUserId) as AssignedToUserId, MAX(TicketComment.DateTime) AS LastDateTime, MAX(Ticket.PriorityId) as PriorityId, MAX(Priority.PriorityName) as PriorityName
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

		SELECT * FROM (SELECT TOP (@TicketsPerPage) * FROM (SELECT TOP (@TicketTo) Ticket.TicketId, MAX(Ticket.Subject) AS Subject, MIN(TicketComment.DateTime) AS DateTime, MAX(Category.CategoryId) AS CategoryId, MAX(Category.CategoryName) AS CategoryName, MAX(State.StateId) AS StateId, MAX(State.StateName) AS StateName, MAX(FirstName) AS FirstName, MAX(LastName) AS LastName, MAX(Ticket.AssignedToUserId) as AssignedToUserId, MAX(TicketComment.DateTime) AS LastDateTime, MAX(Ticket.PriorityId) as PriorityId, MAX(Priority.PriorityName) as PriorityName
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
		ORDER BY MIN(TicketComment.DateTime) DESC) t ORDER BY t.DateTime ASC) tt ORDER BY tt.DateTime DESC

		-- add history event
		SELECT @Action = 'Requested personal tickets; the stored procedure was successfully executed.'
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddHistoryEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime
	END
	ELSE
	BEGIN

		SET @TotalNumberOfTickets =0

		SELECT @TotalNumberOfTickets = COUNT(*) FROM
		(
			SELECT TicketComment.TicketId, MAX(Ticket.Subject) AS Subject, MIN(TicketComment.DateTime) AS DateTime, MAX(Category.CategoryId) AS CategoryId, MAX(Category.CategoryName) AS CategoryName, MAX(State.StateId) AS StateId, MAX(State.StateName) AS StateName, MAX(FirstName) AS FirstName, MAX(LastName) AS LastName, MAX(Ticket.AssignedToUserId) as AssignedToUserId, MAX(TicketComment.DateTime) AS LastDateTime, MAX(Ticket.PriorityId) as PriorityId, MAX(Priority.PriorityName) as PriorityName
			FROM Ticket
			INNER JOIN TicketComment ON Ticket.TicketId = TicketComment.TicketId
			INNER JOIN State ON Ticket.StateId = State.StateId
			INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId
			INNER JOIN UserCategory ON UserCategory.UserId = @UserId AND UserCategory.CategoryId = Ticket.CategoryId
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

		SELECT * FROM (SELECT TOP (@TicketsPerPage) * FROM (SELECT TOP (@TicketTo) Ticket.TicketId, MAX(Ticket.Subject) AS Subject, MIN(TicketComment.DateTime) AS DateTime, MAX(Category.CategoryId) AS CategoryId, MAX(Category.CategoryName) AS CategoryName, MAX(State.StateId) AS StateId, MAX(State.StateName) AS StateName, MAX(FirstName) AS FirstName, MAX(LastName) AS LastName, MAX(Ticket.AssignedToUserId) as AssignedToUserId, MAX(TicketComment.DateTime) AS LastDateTime, MAX(Ticket.PriorityId) as PriorityId, MAX(Priority.PriorityName) as PriorityName
		FROM Ticket
		INNER JOIN TicketComment ON Ticket.TicketId = TicketComment.TicketId
		INNER JOIN State ON Ticket.StateId = State.StateId
		INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId
		INNER JOIN UserCategory ON UserCategory.UserId = @UserId AND UserCategory.CategoryId = Ticket.CategoryId
		LEFT JOIN [User] ON [User].UserId = Ticket.AssignedToUserId
		INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId
		WHERE (@TextToSearch = '')
			OR (@SearchType = 'Subject' AND Subject like ('%' + @TextToSearch + '%'))
			OR (@SearchType = 'Category' AND CategoryName like ('%' + @TextToSearch + '%'))
			OR (@SearchType = 'Status' AND StateName like ('%' + @TextToSearch + '%')) 
		GROUP BY Ticket.TicketId
		ORDER BY min(TicketComment.DateTime) DESC) t ORDER BY t.DateTime ASC) tt ORDER BY tt.DateTime DESC

		-- add history event
		SELECT @Action = 'Requested assigned tickets; the stored procedure was successfully executed.'
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddHistoryEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime
	END
END