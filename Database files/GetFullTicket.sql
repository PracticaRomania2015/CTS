USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetFullTicket]    Script Date: 10/19/2015 3:20:36 PM ******/
DROP PROCEDURE [dbo].[GetFullTicket]
GO

/****** Object:  StoredProcedure [dbo].[GetFullTicket]    Script Date: 10/19/2015 3:20:36 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetFullTicket]
	@TicketId int,
	@Subject varchar(50) OUTPUT,
	@CategoryId int OUTPUT,
	@CategoryName varchar(50) OUTPUT,
	@ParentCategoryId int OUTPUT,
	@StateId int OUTPUT,
	@StateName varchar(50) OUTPUT,
	@AssignedUserId int OUTPUT,
	@AssignedUserFirstName varchar(50) OUTPUT,
	@AssignedUserLastName varchar(50) OUTPUT,
	@PriorityId int OUTPUT,
	@PriorityName varchar(50) OUTPUT,
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	SELECT @DateTime = SYSDATETIME()
	
	SET @ErrCode = 1

	SELECT @Subject = Ticket.Subject, @CategoryId = Category.CategoryId, @CategoryName = Category.CategoryName,@ParentCategoryId = Category.ParentCategoryId, @StateId = State.StateId, @StateName = State.StateName, @PriorityId = Priority.PriorityId, @PriorityName = Priority.PriorityName, @ErrCode = 0
	FROM Ticket
	INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId
	INNER JOIN State ON Ticket.StateId = State.StateId
	INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId
	WHERE Ticket.TicketId = @TicketId

	IF (@ErrCode = 0)
	BEGIN
		SELECT @AssignedUserId = [User].UserId, @AssignedUserFirstName = [User].FirstName, @AssignedUserLastName = [User].LastName
		FROM Ticket
		INNER JOIN [User] ON Ticket.AssignedToUserId = [User].UserId
		WHERE TicketId = @TicketId

		SELECT CommentId, DateTime, Comment, TicketComment.UserId, FirstName, LastName, FilePath
		FROM TicketComment
		INNER JOIN [User] ON TicketComment.UserId = [User].UserId
		WHERE TicketId = @TicketId
		ORDER BY DateTime

		-- set action for audit event
		SELECT @Action = 'The stored procedure to get a full ticket was successfully executed.'
	END
	ELSE
	BEGIN
		-- set action for audit event
		SELECT @Action = 'Failed to get full ticket.'
	END

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = @TicketId
END

GO

