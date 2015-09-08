USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[AssignTicketToAdmin]    Script Date: 9/8/2015 12:03:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[AssignTicketToAdmin]
	@TicketId int,
	@UserId int

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	IF (@UserId = 0)
		BEGIN
			UPDATE Ticket
			SET AssignedToUserId = NULL
			WHERE TicketId = @TicketId

			-- add a new history event
			SELECT @Action = 'Unassigned ticket [TicketId = ' + CONVERT(VARCHAR(25), @TicketId, 126) + ']'
			SELECT @DateTime = SYSDATETIME()

			EXEC dbo.AddHistoryEvent 
			@UserId = @UserId,
			@Action = @Action, 
			@DateTime = @DateTime
		END
	ELSE
		BEGIN
			UPDATE Ticket
			SET AssignedToUserId = @UserId
			WHERE TicketId = @TicketId

			-- add a new history event
			DECLARE @Username varchar(100)

			SELECT @Username = FirstName + LastName
			FROM [User]
			WHERE UserId = @UserId

			SELECT @Action = 'Assigned ticket [TicketId = ' + CONVERT(VARCHAR(25), @TicketId, 126) + '] to ' + @Username
			SELECT @DateTime = SYSDATETIME()

			EXEC dbo.AddHistoryEvent 
			@UserId = @UserId,
			@Action = @Action, 
			@DateTime = @DateTime
		END
END
