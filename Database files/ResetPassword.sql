USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[ResetPassword]    Script Date: 9/18/2015 3:09:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[ResetPassword]
	@Email varchar(50),
	@Password varchar(MAX),
	@Error int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Error = 1
	SELECT top 1 @Error = 0 FROM [User] WHERE Email = @Email

	IF @Error = 0
	BEGIN
		UPDATE [User]
		SET Password = @Password
		WHERE Email = @Email

		-- add a new audit event
		SELECT @Action = 'The password was successfully reseted for the account ' + @Email
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddAuditEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = NULL
	END
	ELSE
	BEGIN
		-- add a new audit event
		SELECT @Action = 'Failed to reset the password for the account ' + @Email
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddAuditEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = NULL
	END
END
