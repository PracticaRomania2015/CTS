USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetFullTicket]    Script Date: 9/11/2015 12:30:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetFullTicket]
	@TicketId int,
	@Subject varchar(50) OUTPUT,
	@CategoryId int OUTPUT,
	@CategoryName varchar(50) OUTPUT,
	@StateId int OUTPUT,
	@StateName varchar(50) OUTPUT,
	@AssignedUserId int OUTPUT,
	@AssignedUserFirstName varchar(50) OUTPUT,
	@AssignedUserLastName varchar(50) OUTPUT,
	@PriorityId int OUTPUT,
	@PriorityName varchar(50) OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	SELECT @Subject = Ticket.Subject, @CategoryId = Category.CategoryId, @CategoryName = Category.CategoryName, @StateId = State.StateId, @StateName = State.StateName, @PriorityId = Priority.PriorityId, @PriorityName = Priority.PriorityName
	FROM Ticket
	INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId
	INNER JOIN State ON Ticket.StateId = State.StateId
	INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId
	WHERE Ticket.TicketId = @TicketId

	SELECT @AssignedUserId = [User].UserId, @AssignedUserFirstName = [User].FirstName, @AssignedUserLastName = [User].LastName
	FROM Ticket
	INNER JOIN [User] ON Ticket.AssignedToUserId = [User].UserId
	WHERE TicketId = @TicketId

	SELECT CommentId, DateTime, Comment, TicketComment.UserId, FirstName, LastName, FilePath
	FROM TicketComment
	INNER JOIN [User] ON TicketComment.UserId = [User].UserId
	WHERE TicketId = @TicketId
	ORDER BY DateTime

	-- add history event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The stored procedure to get comments for the ticket [TicketId = ' + CONVERT(VARCHAR(25), @TicketId, 126) + '] was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddHistoryEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime

END
