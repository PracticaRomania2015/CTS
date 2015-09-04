USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetAdminsForCategory]    Script Date: 9/4/2015 12:09:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetAdminsForCategory]
	@CategoryId int

AS
BEGIN

	SET NOCOUNT ON;
	SELECT [User].UserId, FirstName, LastName
	FROM [User]
	INNER JOIN UserCategory ON [User].UserId = UserCategory.UserId AND CategoryId = @CategoryId
	ORDER BY FirstName, LastName, UserId

END
