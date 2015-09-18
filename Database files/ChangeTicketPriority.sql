USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[ChangeTicketPriority]    Script Date: 9/18/2015 3:05:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[ChangeTicketPriority]
	@TicketId int,
	@PriorityId int,
	@UserId int,
	@Error int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	DECLARE @OldValue varchar(50)
	DECLARE @NewValue varchar(50)

	SELECT @DateTime = SYSDATETIME()
	SELECT @OldValue = (SELECT Priority.PriorityName FROM Ticket INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId WHERE Ticket.TicketId = @TicketId)
	SELECT @NewValue = (SELECT PriorityName FROM Priority WHERE PriorityId = @PriorityId)
	
	SELECT @Error = 1

	SELECT @Error = 0
	FROM Priority
	WHERE PriorityId = @PriorityId

	IF (@Error = 0)
	BEGIN
		UPDATE Ticket
		SET PriorityId = @PriorityId
		WHERE TicketId = @TicketId
		
		-- add a new ticket history event
		SELECT @Action = 'Change priority'

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
		SELECT @Action = 'Failed to change the ticket priority caused by bad ticket id or bad priority id.'	

		EXEC dbo.AddAuditEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = @TicketId
	END
END
