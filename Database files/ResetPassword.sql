USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[ResetPassword]    Script Date: 10/8/2015 1:21:11 PM ******/
DROP PROCEDURE [dbo].[ResetPassword]
GO

/****** Object:  StoredProcedure [dbo].[ResetPassword]    Script Date: 10/8/2015 1:21:11 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ResetPassword]
	@Email varchar(50),
	@Password varchar(MAX),
	@Question_1_Id int,
	@Question_1_Answer varchar(255),
	@Question_2_Id int,
	@Question_2_Answer varchar(255),
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
	WHERE Email = @Email AND Question_1_Id = @Question_1_Id AND Question_1_Answer = @Question_1_Answer AND Question_2_Id = @Question_2_Id AND Question_2_Answer = @Question_2_Answer

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

