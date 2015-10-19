USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[AssignTicketToAdmin]    Script Date: 10/19/2015 3:18:25 PM ******/
DROP PROCEDURE [dbo].[AssignTicketToAdmin]
GO

/****** Object:  StoredProcedure [dbo].[AssignTicketToAdmin]    Script Date: 10/19/2015 3:18:25 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AssignTicketToAdmin]
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

	-- set params for histort event
	SELECT @DateTime = SYSDATETIME()
	SELECT @OldValue = (SELECT AssignedToUserId FROM Ticket WHERE TicketId = @TicketId)

	IF (@UserId = 0)
		BEGIN
			UPDATE Ticket
			SET AssignedToUserId = NULL
			WHERE TicketId = @TicketId

			-- set params for histort event
			SELECT @Action = 'Unassign'
			SELECT @NewValue = ''
		END
	ELSE
		BEGIN
			UPDATE Ticket
			SET AssignedToUserId = @UserId
			WHERE TicketId = @TicketId

			-- set params for histort event
			SELECT @Action = 'Assign'
			SELECT @NewValue = @UserId
		END

	-- add a new ticket history event
	EXEC dbo.AddTicketHistoryEvent 
	@TicketId = @TicketId,
	@UserId = @UserWhoDoTheAssignId,
	@DateTime = @DateTime,
	@Action = @Action,
	@OldValue = @OldValue,
	@NewValue = @NewValue
END

GO

