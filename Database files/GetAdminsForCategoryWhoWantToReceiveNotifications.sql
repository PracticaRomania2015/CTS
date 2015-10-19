USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetAdminsForCategoryWhoWantToReceiveNotifications]    Script Date: 10/19/2015 3:20:12 PM ******/
DROP PROCEDURE [dbo].[GetAdminsForCategoryWhoWantToReceiveNotifications]
GO

/****** Object:  StoredProcedure [dbo].[GetAdminsForCategoryWhoWantToReceiveNotifications]    Script Date: 10/19/2015 3:20:12 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetAdminsForCategoryWhoWantToReceiveNotifications]
	@CategoryId int

AS
BEGIN

	SET NOCOUNT ON;

	SELECT @CategoryId = ParentCategoryId
	FROM Category
	WHERE CategoryId = @CategoryId AND ParentCategoryId IS NOT NULL

	SELECT FirstName, LastName, Email
	FROM [User]
	INNER JOIN UserCategory ON [User].UserId = UserCategory.UserId
	WHERE UserCategory.CategoryId = @CategoryId AND UserCategory.GetEmailForNewTicket = 1

END

GO

