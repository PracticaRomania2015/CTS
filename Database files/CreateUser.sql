USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[CreateUser]    Script Date: 9/15/2015 9:47:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[CreateUser]
	@FirstName varchar(50),
	@LastName varchar(50),
	@Title varchar(10),
	@Email varchar(50),
	@Password varchar(50),
	@Error int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	IF NOT EXISTS (SELECT UserId FROM [User] WHERE Email = @Email)
	BEGIN
		INSERT INTO [User] (FirstName, LastName, Title, Email, Password, RoleId)
		VALUES (@FirstName, @LastName, @Title, @Email, @Password, 1)
		SELECT @Error = 0


		-- add history event
		SELECT @Action = 'A new account was successfully created. [' + @Email + ']'
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddHistoryEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime
	END
	ELSE
	BEGIN
		SELECT @Error = 1


		-- add history event
		SELECT @Action = 'Failed to create a new account. Email already exists. [' + @Email + ']'
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddHistoryEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime
	END
END
