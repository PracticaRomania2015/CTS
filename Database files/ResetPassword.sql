USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[ResetPassword]    Script Date: 9/21/2015 12:53:47 PM ******/
DROP PROCEDURE [dbo].[ResetPassword]
GO

/****** Object:  StoredProcedure [dbo].[ResetPassword]    Script Date: 9/21/2015 12:53:47 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ResetPassword]
	@Email varchar(50),
	@Password varchar(MAX),
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @DateTime = SYSDATETIME()

	SELECT @ErrCode = 1
	
	SELECT @Action = 'The password was successfully reseted for the account ' + @Email

	UPDATE [User]
	SET Password = @Password, @ErrCode = 0
	WHERE Email = @Email

	IF (@ErrCode = 1)
	BEGIN
		-- if the password wasn't reseted then set an error message for audit event
		SELECT @Action = 'Failed to reset the password for the account ' + @Email
	END

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END

GO

