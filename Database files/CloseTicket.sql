USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[CloseTicket]    Script Date: 9/8/2015 12:04:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[CloseTicket]
	@TicketId int

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @StateId int
	SELECT @StateId = StateId
	FROM State
	WHERE StateName = 'Closed'

	UPDATE Ticket
	SET StateId = @StateId
	WHERE TicketId = @TicketId
	
	-- add history event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The ticket [TicketId = ' + CONVERT(VARCHAR(25), @TicketId, 126) + '] was closed'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddHistoryEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime

END
