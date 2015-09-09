USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[CloseTicket]    Script Date: 9/9/2015 11:40:44 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[CloseTicket]
	@TicketId int,
	@Error int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

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
		-- add history event
		SELECT @Action = 'The ticket [TicketId = ' + CONVERT(VARCHAR(25), @TicketId, 126) + '] was closed'
		
		EXEC dbo.AddHistoryEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime
	END
	ELSE
	BEGIN
		-- add history event
		SELECT @Action = 'Failed when trying to close the ticket [TicketId = ' + CONVERT(VARCHAR(25), @TicketId, 126) + '].'

		EXEC dbo.AddHistoryEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime
	END

END
