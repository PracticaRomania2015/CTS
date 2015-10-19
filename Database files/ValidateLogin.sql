USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[ValidateLogin]    Script Date: 10/19/2015 3:22:44 PM ******/
DROP PROCEDURE [dbo].[ValidateLogin]
GO

/****** Object:  StoredProcedure [dbo].[ValidateLogin]    Script Date: 10/19/2015 3:22:44 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ValidateLogin]
	@UserId int OUTPUT,
	@FirstName varchar(50) OUTPUT,
	@LastName varchar(50) OUTPUT,
	@Title varchar(10) OUTPUT,
	@Email varchar(50),
	@Password varchar(50),
	@RoleId int OUTPUT,
	@RoleName varchar(50) OUTPUT,
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @DateTime = SYSDATETIME()

	SET @ErrCode = 1

	SELECT @UserId = UserId, @FirstName = FirstName, @LastName = LastName, @Title = Title, @RoleId = Role.RoleId, @RoleName = Role.RoleName, @ErrCode = 0
	FROM [User]
	INNER JOIN Role ON [User].RoleId = Role.RoleId
	WHERE Email = @Email AND Password = @Password

	IF (@ErrCode = 0)
	BEGIN
		-- set successfully message for audit event
		SELECT @Action = 'Login successfully.'
	END
	ELSE
	BEGIN
		-- check if the email is invalid or the password is invalid
		DECLARE @Check int = 0

		SELECT @UserId = UserId, @Check = 1
		FROM [User]
		WHERE Email = @Email

		IF (@Check = 1)
		BEGIN
			-- set error message for audit event (invalid password)
			SELECT @Action = 'Failed to login. Invalid password.'
		END
		ELSE
		BEGIN
			-- set error message for audit event (invalid email)
			SELECT @Action = 'Failed to login. Invalid email.'
		END	
	END

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = @UserId,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END

GO

