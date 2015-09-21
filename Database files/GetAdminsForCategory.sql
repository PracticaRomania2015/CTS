USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetAdminsForCategory]    Script Date: 9/21/2015 12:52:39 PM ******/
DROP PROCEDURE [dbo].[GetAdminsForCategory]
GO

/****** Object:  StoredProcedure [dbo].[GetAdminsForCategory]    Script Date: 9/21/2015 12:52:39 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetAdminsForCategory]
	@CategoryId int

AS
BEGIN

	SET NOCOUNT ON;
	SELECT [User].UserId, FirstName, LastName
	FROM [User]
	INNER JOIN Category ON Category.CategoryId = @CategoryId
	INNER JOIN UserCategory ON [User].UserId = UserCategory.UserId AND (UserCategory.CategoryId = @CategoryId OR UserCategory.CategoryId = Category.ParentCategoryId)
	ORDER BY FirstName, LastName, UserId

	-- add a new audit event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The stored procedure to get admins for ' + (SELECT CategoryName FROM Category WHERE CategoryId = @CategoryId) + ' category was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL

END

GO

