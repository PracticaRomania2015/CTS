USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[AddCommentToTicket]    Script Date: 10/8/2015 1:17:26 PM ******/
DROP PROCEDURE [dbo].[AddCommentToTicket]
GO

/****** Object:  StoredProcedure [dbo].[AddCommentToTicket]    Script Date: 10/8/2015 1:17:26 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AddCommentToTicket]
	@CommentId int OUTPUT,
	@TicketId int,
	@DateTime datetime,
	@Comment varchar(2000),
	@UserId int,
	@FilePath varchar(100),
	@ErrCode int OUTPUT

AS
BEGIN
	
	SET NOCOUNT ON;
	DECLARE @Check int = 0

	IF (@TicketId != 0 AND @TicketId != 0 AND @Comment != '' AND @UserId != 0)
		BEGIN
			-- check if the ticket is closed or not
			SELECT @Check = 1
			FROM Ticket
			INNER JOIN State ON Ticket.StateId = State.StateId
			WHERE State.StateName = 'Closed' AND Ticket.TicketId = @TicketId

			IF (@Check = 0)
			BEGIN
				-- add the new comment
				INSERT INTO TicketComment(TicketId, DateTime, Comment, UserId, FilePath)
				VALUES (@TicketId, @DateTime, @Comment, @UserId, @FilePath)

				IF (@@ROWCOUNT = 0 OR @@ERROR <> 0)
				BEGIN
					SELECT @ErrCode = 1
					RETURN 1
				END

				-- get the id for added comment
				SELECT @CommentId = @@IDENTITY

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

				SELECT @ErrCode = 1

				-- change ticket state
				UPDATE Ticket
				SET Ticket.StateId = @StateId, @ErrCode = 0
				WHERE Ticket.TicketId = @TicketId
			
				-- add a new ticket history event
				DECLARE @Action varchar(1000)
				SELECT @Action = 'Add comment'

				EXEC dbo.AddTicketHistoryEvent 
				@TicketId = @TicketId,
				@UserId = @UserId,
				@DateTime = @DateTime,
				@Action = @Action,
				@OldValue = '',
				@NewValue = ''
			END
			ELSE
			BEGIN
				-- set error parameter
				SELECT @ErrCode = 1
			END
		END
	ELSE
		BEGIN
			-- set error parameter
			SELECT @ErrCode = 1
		END
END

GO

