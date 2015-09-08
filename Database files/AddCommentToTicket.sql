USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[AddCommentToTicket]    Script Date: 9/8/2015 12:03:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[AddCommentToTicket]
	@CommentId int OUTPUT,
	@TicketId int,
	@DateTime datetime,
	@Comment varchar(250),
	@UserId int,
	@FilePath varchar(100),
	@Error int OUTPUT

AS
BEGIN
	
	SET NOCOUNT ON;

	IF (@TicketId != 0 AND @TicketId != 0 AND @Comment != '' AND @UserId != 0)
		BEGIN
			-- add the new comment
			INSERT INTO TicketComment(TicketId, DateTime, Comment, UserId, FilePath)
			VALUES (@TicketId, @DateTime, @Comment, @UserId, @FilePath)

			-- get the id for added comment
			SELECT TOP 1 @CommentId = CommentId FROM TicketComment ORDER BY CommentId DESC

			-- get the user who submitted the ticket
			DECLARE @TicketUser int
			SELECT TOP 1 @TicketUser = TicketComment.UserId
			FROM TicketComment
			WHERE TicketComment.TicketId = @TicketId
			ORDER BY TicketComment.DateTime ASC

			-- verify if the user is admin or not
			DECLARE @CheckIfUserIsAdmin int = 0
			SELECT @CheckIfUserIsAdmin = 1
			FROM Ticket
			INNER JOIN UserCategory ON UserCategory.CategoryId = Ticket.CategoryId AND UserCategory.UserId = @UserId

			-- if the user is admin and is not the user who submitted the ticket then the state will be answered
			-- (that means an admin for the ticket category posts a new comment)
			-- otherwise the state will be active (that means the user who submitted the ticket posts a new comment)
			DECLARE @StateId int
			IF (@TicketUser != @UserId AND @CheckIfUserIsAdmin = 1)
				BEGIN
					SELECT @StateId = State.StateId
					FROM State
					WHERE State.StateName = 'Answered'
				END
			ELSE
				BEGIN
					SELECT @StateId = State.StateId
					FROM State
					WHERE State.StateName = 'Active'
				END

			-- change ticket state
			UPDATE Ticket
			SET Ticket.StateId = @StateId
			WHERE Ticket.TicketId = @TicketId
			
			-- add a new history event
			DECLARE @Action varchar(1000)
			SELECT @Action = 'Added a new comment to a ticket [TicketId = ' + CONVERT(VARCHAR(25), @TicketId, 126) + ']: ' + @Comment
			EXEC dbo.AddHistoryEvent 
			@UserId = @UserId,
			@Action = @Action,
			@DateTime = @DateTime

			-- set error parameter to 0 (everything goes fine)
			SELECT @Error = 0
		END
	ELSE
		BEGIN
			-- set error parameter to 1 (invalid data)
			SELECT @Error = 1
		END
	
END
