USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetUserData]    Script Date: 10/8/2015 1:20:48 PM ******/
DROP PROCEDURE [dbo].[GetUserData]
GO

/****** Object:  StoredProcedure [dbo].[GetUserData]    Script Date: 10/8/2015 1:20:48 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetUserData]
	@UserId int,
	@FirstName varchar(50) OUTPUT,
	@LastName varchar(50) OUTPUT,
	@Title varchar(10) OUTPUT,
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	SET @ErrCode = 1

	SELECT @FirstName = FirstName, @LastName = LastName, @Title = Title, @ErrCode = 0
	FROM [User]
	WHERE UserId = @UserId

END

GO

