USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[DeleteUser]    Script Date: 9/18/2015 3:06:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[DeleteUser]
	@UserId int

AS
BEGIN

	SET NOCOUNT ON;

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
