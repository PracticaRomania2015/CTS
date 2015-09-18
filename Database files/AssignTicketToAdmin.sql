USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[AssignTicketToAdmin]    Script Date: 9/18/2015 3:03:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[AssignTicketToAdmin]
	@TicketId int,
	@UserId int,
	@UserWhoDoTheAssignId int

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	DECLARE @OldValue varchar(50)
	DECLARE @NewValue varchar(50)

	SELECT @DateTime = SYSDATETIME()
	SELECT @OldValue = (SELECT AssignedToUserId FROM Ticket WHERE TicketId = @TicketId)

	IF (@UserId = 0)
		BEGIN
			UPDATE Ticket
			SET AssignedToUserId = NULL
			WHERE TicketId = @TicketId

			-- add a new ticket history event
			SELECT @Action = 'Unassign'
			SELECT @NewValue = ''
	
			EXEC dbo.AddTicketHistoryEvent 
			@TicketId = @TicketId,
			@UserId = @UserWhoDoTheAssignId,
			@DateTime = @DateTime,
			@Action = @Action,
			@OldValue = @OldValue,
			@NewValue = @NewValue
		END
	ELSE
		BEGIN
			UPDATE Ticket
			SET AssignedToUserId = @UserId
			WHERE TicketId = @TicketId

			-- add a new ticket history event
			SELECT @Action = 'Assign'
			SELECT @NewValue = @UserId

			EXEC dbo.AddTicketHistoryEvent 
			@TicketId = @TicketId,
			@UserId = @UserWhoDoTheAssignId,
			@DateTime = @DateTime,
			@Action = @Action,
			@OldValue = @OldValue,
			@NewValue = @NewValue
		END
END
