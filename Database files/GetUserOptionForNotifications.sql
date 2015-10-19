USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetUserOptionForNotifications]    Script Date: 10/19/2015 3:21:54 PM ******/
DROP PROCEDURE [dbo].[GetUserOptionForNotifications]
GO

/****** Object:  StoredProcedure [dbo].[GetUserOptionForNotifications]    Script Date: 10/19/2015 3:21:54 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetUserOptionForNotifications]
	@UserId int,
	@GetEmailForTicketResponse bit OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	SELECT @GetEmailForTicketResponse = GetEmailForTicketResponse
	FROM [User]
	WHERE UserId = @UserId

END

GO

