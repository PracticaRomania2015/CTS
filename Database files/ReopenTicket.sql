USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[ReopenTicket]    Script Date: 9/18/2015 3:08:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[ReopenTicket]
	@TicketId int,
	@UserId int,
	@Error int OUTPUT

AS
BEGIN
	
	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	DECLARE @OldValue varchar(50)
	DECLARE @NewValue varchar(50)

	SELECT @OldValue = 'Closed'

	SELECT @DateTime = SYSDATETIME()

	DECLARE @StateId int
	DECLARE @FirstCommentUser int
	DECLARE @LastCommentUser int

	SELECT TOP 1 @FirstCommentUser = TicketComment.UserId
	FROM TicketComment
	WHERE TicketComment.TicketId = @TicketId
	ORDER BY TicketComment.DateTime ASC

	SELECT TOP 1 @LastCommentUser = TicketComment.UserId
	FROM TicketComment
	WHERE TicketComment.TicketId = @TicketId
	ORDER BY TicketComment.DateTime DESC

	IF (@FirstCommentUser = @LastCommentUser)
	BEGIN
		SELECT @StateId = StateId
		FROM State
		WHERE StateName = 'Active'

		SELECT @NewValue = 'Active'
	END
	ELSE
	BEGIN
		SELECT @StateId = StateId
		FROM State
		WHERE StateName = 'Answered'

		SELECT @NewValue = 'Answered'
	END

	SELECT @Error = 1

	UPDATE Ticket
	SET StateId = @StateId, @Error = 0
	WHERE TicketId = @TicketId
	
	IF (@Error = 0)
	BEGIN
		-- add a new ticket history event
		SELECT @Action = 'Reopen'

		EXEC dbo.AddTicketHistoryEvent 
		@TicketId = @TicketId,
		@UserId = @UserId,
		@DateTime = @DateTime,
		@Action = @Action,
		@OldValue = @OldValue,
		@NewValue = @NewValue
	END
	ELSE
	BEGIN
		-- add a new audit event
		SELECT @Action = 'Failed when trying to reopen the ticket.'

		EXEC dbo.AddAuditEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = @TicketId
	END

END
