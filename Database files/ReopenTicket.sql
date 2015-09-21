USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[ReopenTicket]    Script Date: 9/21/2015 12:53:40 PM ******/
DROP PROCEDURE [dbo].[ReopenTicket]
GO

/****** Object:  StoredProcedure [dbo].[ReopenTicket]    Script Date: 9/21/2015 12:53:40 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ReopenTicket]
	@TicketId int,
	@UserId int,
	@ErrCode int OUTPUT

AS
BEGIN
	
	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	DECLARE @OldValue varchar(50)
	DECLARE @NewValue varchar(50)

	SELECT @OldValue = 'Closed'

	SELECT @DateTime = SYSDATETIME()
	SELECT @Action = 'Reopen'

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
		SELECT @StateId = StateId, @NewValue = StateName
		FROM State
		WHERE StateName = 'Active'
	END
	ELSE
	BEGIN
		SELECT @StateId = StateId, @NewValue = StateName
		FROM State
		WHERE StateName = 'Answered'
	END

	SELECT @ErrCode = 1

	UPDATE Ticket
	SET StateId = @StateId, @ErrCode = 0
	WHERE TicketId = @TicketId
	
	IF (@ErrCode = 1)
	BEGIN
		-- if the ticket was not reopened then set the new value to old value
		SELECT @NewValue = @OldValue
	END

	-- add a new ticket history event
	EXEC dbo.AddTicketHistoryEvent 
	@TicketId = @TicketId,
	@UserId = @UserId,
	@DateTime = @DateTime,
	@Action = @Action,
	@OldValue = @OldValue,
	@NewValue = @NewValue
END

GO

