USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[CreateUser]    Script Date: 9/4/2015 12:08:49 PM ******/
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

	IF NOT EXISTS (SELECT UserId FROM [User] WHERE Email = @Email)
	BEGIN
		INSERT INTO [User] (FirstName, LastName, Title, Email, Password)
		VALUES (@FirstName, @LastName, @Title, @Email, @Password)
		SELECT @Error = 0
	END
	ELSE
		SELECT @Error = 1

END
