USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[ValidateLogin]    Script Date: 9/18/2015 3:09:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[ValidateLogin]
	@UserId int OUTPUT,
	@FirstName varchar(50) OUTPUT,
	@LastName varchar(50) OUTPUT,
	@Title varchar(10) OUTPUT,
	@Email varchar(50),
	@Password varchar(50),
	@RoleId int OUTPUT,
	@RoleName varchar(50) OUTPUT,
	@Error int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @UserId = UserId, @FirstName = FirstName, @LastName = LastName, @Title = Title, @RoleId = Role.RoleId, @RoleName = Role.RoleName, @Error = 0
	FROM [User]
	INNER JOIN Role ON [User].RoleId = Role.RoleId
	WHERE Email = @Email AND Password = @Password

	IF (@Error = 0)
	BEGIN
		-- add a new audit event
		SELECT @Action = 'Login successfully.'
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddAuditEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = NULL
	END
	ELSE
	BEGIN
		-- add a new audit event
		DECLARE @UserIdForHistory int
		DECLARE @Check int = 0

		SELECT @UserIdForHistory = UserId, @Check = 1
		FROM [User]
		WHERE Email = @Email

		IF (@Check = 1)
		BEGIN
			SELECT @Action = 'Failed to login. Invalid password.'
		END
		ELSE
		BEGIN
			SELECT @Action = 'Failed to login. Invalid email.'
		END

		SELECT @DateTime = SYSDATETIME()		

		EXEC dbo.AddAuditEvent 
		@UserId = @UserIdForHistory,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = NULL
	END
END
