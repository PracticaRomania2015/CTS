USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[DeleteTicket]    Script Date: 10/8/2015 1:18:33 PM ******/
DROP PROCEDURE [dbo].[DeleteTicket]
GO

/****** Object:  StoredProcedure [dbo].[DeleteTicket]    Script Date: 10/8/2015 1:18:33 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[DeleteTicket]
	@TicketId int

AS
BEGIN

	SET NOCOUNT ON;
	
	IF @TicketId = 0
	BEGIN
		SELECT TOP 1 @TicketId = TicketId FROM Ticket ORDER BY TicketId DESC
	END

	DELETE FROM Audit WHERE TicketId = @TicketId
	DELETE FROM TicketsHistory WHERE TicketId = @TicketId
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

GO

