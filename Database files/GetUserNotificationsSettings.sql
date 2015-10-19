USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetUserNotificationsSettings]    Script Date: 10/19/2015 3:21:44 PM ******/
DROP PROCEDURE [dbo].[GetUserNotificationsSettings]
GO

/****** Object:  StoredProcedure [dbo].[GetUserNotificationsSettings]    Script Date: 10/19/2015 3:21:44 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetUserNotificationsSettings]
	@UserId int,
	@GetEmailForTicketResponse bit OUTPUT,
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;
	
	SET @ErrCode = 1

	SELECT @GetEmailForTicketResponse = GetEmailForTicketResponse, @ErrCode = 0
	FROM [User]
	WHERE UserId = @UserId

	SELECT Category.CategoryId, CategoryName, GetEmailForNewTicket, GetEmailForNewComment
	FROM UserCategory
	INNER JOIN Category ON UserCategory.CategoryId = Category.CategoryId
	WHERE UserCategory.UserId = @UserId

END

GO

