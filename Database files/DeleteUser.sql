USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[DeleteUser]    Script Date: 9/4/2015 12:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[DeleteUser]
	@UserId int

AS
BEGIN

	SET NOCOUNT ON;

	DELETE FROM [User] WHERE UserId = @UserId

END
