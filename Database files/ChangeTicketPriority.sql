USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[ChangeTicketPriority]    Script Date: 10/8/2015 1:18:00 PM ******/
DROP PROCEDURE [dbo].[ChangeTicketPriority]
GO

/****** Object:  StoredProcedure [dbo].[ChangeTicketPriority]    Script Date: 10/8/2015 1:18:00 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ChangeTicketPriority]
	@TicketId int,
	@PriorityId int,
	@UserId int,
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	DECLARE @OldValue varchar(50)
	DECLARE @NewValue varchar(50)

	SELECT @DateTime = SYSDATETIME()
	SELECT @Action = 'Change priority'
	SELECT @OldValue = (SELECT Priority.PriorityName FROM Ticket INNER JOIN Priority ON Ticket.PriorityId = Priority.PriorityId WHERE Ticket.TicketId = @TicketId)
	SELECT @NewValue = (SELECT PriorityName FROM Priority WHERE PriorityId = @PriorityId)
	
	SELECT @ErrCode = 1

	-- change the priority
	UPDATE Ticket
	SET PriorityId = @PriorityId, @ErrCode = 0
	WHERE TicketId = @TicketId

	IF (@ErrCode = 1)
	BEGIN
		-- if the priority was not changed then change the new value for history event into old value
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

