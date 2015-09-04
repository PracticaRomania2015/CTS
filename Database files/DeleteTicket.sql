USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[DeleteTicket]    Script Date: 9/4/2015 12:08:57 PM ******/
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

END
