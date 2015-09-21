USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[DeleteUser]    Script Date: 9/21/2015 12:52:30 PM ******/
DROP PROCEDURE [dbo].[DeleteUser]
GO

/****** Object:  StoredProcedure [dbo].[DeleteUser]    Script Date: 9/21/2015 12:52:30 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[DeleteUser]
	@UserId int

AS
BEGIN

	SET NOCOUNT ON;

	-- this stored procedure is just for testing purposes

	DELETE FROM Audit WHERE UserId = @UserId
	DELETE FROM TicketsHistory WHERE UserId = @UserId
	DELETE FROM [User] WHERE UserId = @UserId

	-- add a new audit event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The user was deleted'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddAuditEvent 
	@UserId = @UserId,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL

END

GO

