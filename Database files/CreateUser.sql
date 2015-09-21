USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[CreateUser]    Script Date: 9/21/2015 12:52:09 PM ******/
DROP PROCEDURE [dbo].[CreateUser]
GO

/****** Object:  StoredProcedure [dbo].[CreateUser]    Script Date: 9/21/2015 12:52:09 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[CreateUser]
	@FirstName varchar(50),
	@LastName varchar(50),
	@Title varchar(10),
	@Email varchar(50),
	@Password varchar(50),
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @DateTime = SYSDATETIME()
	SELECT @Action = 'A new account was successfully created. [' + @Email + ']'
	SET @ErrCode = 0

	IF NOT EXISTS (SELECT UserId FROM [User] WHERE Email = @Email)
	BEGIN
		INSERT INTO [User] (FirstName, LastName, Title, Email, Password, RoleId)
		VALUES (@FirstName, @LastName, @Title, @Email, @Password, 1)

		IF (@@ROWCOUNT = 0 OR @@ERROR <> 0)
		BEGIN
			SET @ErrCode = 1
		END
	END

	IF (@ErrCode = 1)
	BEGIN
		-- if the account was not created then set param fail message for audit event
		SELECT @Action = 'Failed to create a new account. Email already exists. [' + @Email + ']'
	END

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END

GO

