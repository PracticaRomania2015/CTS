USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[CloseTicket]    Script Date: 9/4/2015 12:08:16 PM ******/
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

END
