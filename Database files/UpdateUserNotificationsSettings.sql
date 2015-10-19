USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[UpdateUserNotificationsSettings]    Script Date: 10/19/2015 3:22:53 PM ******/
DROP PROCEDURE [dbo].[UpdateUserNotificationsSettings]
GO

/****** Object:  StoredProcedure [dbo].[UpdateUserNotificationsSettings]    Script Date: 10/19/2015 3:22:53 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[UpdateUserNotificationsSettings]
	@UserId int,
	@GetEmailForTicketResponse bit,
	@CategoryIdListForGetEmailForNewTicket varchar(500),
	@CategoryIdListForGetEmailforNewComment varchar(500),
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	SET @ErrCode = 1

	UPDATE [User]
	SET GetEmailForTicketResponse = @GetEmailForTicketResponse, @ErrCode = 0
	WHERE UserId = @UserId

	IF (@ErrCode = 1)
		RETURN;

	UPDATE UserCategory
	SET GetEmailForNewTicket = 0, GetEmailForNewComment = 0
	WHERE UserId = @UserId

	DECLARE @SQL varchar(600)

	IF (@CategoryIdListForGetEmailForNewTicket != '')
	BEGIN
		SET @SQL = 
			'UPDATE	UserCategory
			SET GetEmailForNewTicket = 1
			WHERE UserId = ' + CONVERT(varchar(10), @UserId) + ' AND CategoryId IN (' + @CategoryIdListForGetEmailForNewTicket + ')'
		EXEC(@SQL)
	END

	IF (@CategoryIdListForGetEmailforNewComment != '')
	BEGIN
		SET @SQL = 
			'UPDATE	UserCategory
			SET GetEmailForNewComment = 1
			WHERE UserId = ' + CONVERT(varchar(10), @UserId) + ' AND CategoryId IN (' + @CategoryIdListForGetEmailforNewComment + ')'
		EXEC(@SQL)
	END
END
GO

