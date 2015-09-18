USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[CloseTicket]    Script Date: 9/18/2015 3:06:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[CloseTicket]
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

	SELECT @OldValue = State.StateName FROM Ticket INNER JOIN State ON Ticket.StateId = State.StateId WHERE Ticket.TicketId = @TicketId

	SELECT @DateTime = SYSDATETIME()

	DECLARE @StateId int
	SELECT @StateId = StateId
	FROM State
	WHERE StateName = 'Closed'

	SELECT @Error = 1

	UPDATE Ticket
	SET StateId = @StateId, @Error = 0
	WHERE TicketId = @TicketId
	
	IF (@Error = 0)
	BEGIN
		-- add a new ticket history event
		SELECT @Action = 'Close'
		SELECT @NewValue = 'Close'

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
		SELECT @Action = 'Failed when trying to close the ticket.'

		EXEC dbo.AddAuditEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = @TicketId
	END
END
