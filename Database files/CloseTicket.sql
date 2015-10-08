USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[CloseTicket]    Script Date: 10/8/2015 1:18:08 PM ******/
DROP PROCEDURE [dbo].[CloseTicket]
GO

/****** Object:  StoredProcedure [dbo].[CloseTicket]    Script Date: 10/8/2015 1:18:08 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[CloseTicket]
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

	SELECT @OldValue = State.StateName FROM Ticket INNER JOIN State ON Ticket.StateId = State.StateId WHERE Ticket.TicketId = @TicketId
	SELECT @NewValue = 'Close'
	SELECT @Action = 'Close'
	SELECT @DateTime = SYSDATETIME()

	DECLARE @StateId int
	SELECT @StateId = StateId
	FROM State
	WHERE StateName = 'Closed'

	SELECT @ErrCode = 1

	UPDATE Ticket
	SET StateId = @StateId, @ErrCode = 0
	WHERE TicketId = @TicketId
	
	IF (@ErrCode = 1)
	BEGIN
		-- if the ticket was not closed then change the new value for history event into old value
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

