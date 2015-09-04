USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[ValidateLogin]    Script Date: 9/4/2015 12:09:59 PM ******/
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

	SELECT @UserId = UserId, @FirstName = FirstName, @LastName = LastName, @Title = Title, @Error = 0
	FROM [User]
	WHERE Email = @Email AND Password = @Password

END
