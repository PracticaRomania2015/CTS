USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[ChangeTicketPriority]    Script Date: 9/9/2015 10:44:44 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[ChangeTicketPriority]
	@TicketId int,
	@PriorityId int,
	@Error int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	SELECT @DateTime = SYSDATETIME()
	
	SELECT @Error = 1

	UPDATE Ticket
	SET PriorityId = @PriorityId, @Error = 0
	WHERE TicketId = @TicketId

	IF (@Error = 0)
	BEGIN
		-- add history event
		SELECT @Action = 'Change ticket [TicketId = ' + CONVERT(VARCHAR(25), @TicketId, 126) + '] priority to ' + (SELECT PriorityName FROM Priority WHERE PriorityId = @PriorityId)

		EXEC dbo.AddHistoryEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime
	END
	ELSE
	BEGIN
		-- add history event
		SELECT @Action = 'Failed to change the ticket priority caused by bad ticket id or bad priority id. Details: TicketId received is ' + CONVERT(VARCHAR(25), @TicketId, 126) + ', PriorityId received is ' + CONVERT(VARCHAR(25), @PriorityId, 126) + '.'	

		EXEC dbo.AddHistoryEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime
	END
END
