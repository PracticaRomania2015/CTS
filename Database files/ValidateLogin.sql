USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[ValidateLogin]    Script Date: 9/9/2015 9:53:43 AM ******/
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
	@Error int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @UserId = UserId, @FirstName = FirstName, @LastName = LastName, @Title = Title, @Error = 0
	FROM [User]
	WHERE Email = @Email AND Password = @Password

	IF (@Error = 0)
	BEGIN
		-- add history event
		SELECT @Action = 'Login successfully.'
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddHistoryEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime
	END
	ELSE
	BEGIN
		-- add history event
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

		EXEC dbo.AddHistoryEvent 
		@UserId = @UserIdForHistory,
		@Action = @Action, 
		@DateTime = @DateTime
	END
END
