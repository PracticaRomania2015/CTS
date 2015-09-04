USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[ResetPassword]    Script Date: 9/4/2015 12:09:53 PM ******/
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
	SELECT @Error = 1
	SELECT top 1 @Error = 0 FROM [User] WHERE Email = @Email

	IF @Error = 0
	BEGIN
		UPDATE [User]
		SET Password = @Password
		WHERE Email = @Email
	END

END
