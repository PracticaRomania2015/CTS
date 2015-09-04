USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[AddCommentToTicket]    Script Date: 9/4/2015 12:06:06 PM ******/
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
			INSERT INTO TicketComment(TicketId, DateTime, Comment, UserId, FilePath)
			VALUES (@TicketId, @DateTime, @Comment, @UserId, @FilePath)

			SELECT TOP 1 @CommentId = CommentId FROM TicketComment ORDER BY CommentId DESC

			DECLARE @TicketUser int
			SELECT TOP 1 @TicketUser = TicketComment.UserId
			FROM TicketComment
			WHERE TicketComment.TicketId = @TicketId
			ORDER BY TicketComment.DateTime ASC

			DECLARE @CheckIfUserIsAdmin int = 0
			SELECT @CheckIfUserIsAdmin = 1
			FROM Ticket
			INNER JOIN UserCategory ON UserCategory.CategoryId = Ticket.CategoryId AND UserCategory.UserId = @UserId

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

			UPDATE Ticket
			SET Ticket.StateId = @StateId
			WHERE Ticket.TicketId = @TicketId

			SELECT @Error = 0
		END
	ELSE
		BEGIN
			SELECT @Error = 1
		END
	
END
