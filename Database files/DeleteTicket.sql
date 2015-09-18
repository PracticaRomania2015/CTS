USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[DeleteTicket]    Script Date: 9/18/2015 3:06:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[DeleteTicket]
	@TicketId int

AS
BEGIN

	SET NOCOUNT ON;
	
	IF @TicketId = 0
		SELECT TOP 1 @TicketId = TicketId FROM Ticket ORDER BY TicketId DESC

	DELETE FROM TicketComment WHERE TicketId = @TicketId
	DELETE FROM Ticket WHERE TicketId = @TicketId

	-- add a new audit event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The ticket was deleted.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = @TicketId

END
