USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetTicketUser]    Script Date: 10/8/2015 1:20:37 PM ******/
DROP PROCEDURE [dbo].[GetTicketUser]
GO

/****** Object:  StoredProcedure [dbo].[GetTicketUser]    Script Date: 10/8/2015 1:20:37 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetTicketUser]
	@TicketId int,
	@FirstName varchar(50) OUT,
	@LastName varchar(50) OUT,
	@Email varchar(50) OUT,
	@ErrCode int OUT

AS
BEGIN

	SET NOCOUNT ON;

	SET @ErrCode = 1

	SELECT TOP 1 @FirstName = [User].FirstName, @LastName = [User].LastName, @Email = [User].Email, @ErrCode = 0
	FROM TicketComment
	INNER JOIN [User] ON TicketComment.UserId = [User].UserId
	WHERE TicketComment.TicketId = @TicketId
	ORDER BY TicketComment.DateTime ASC

END

GO

