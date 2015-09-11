USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetAdminsForCategory]    Script Date: 9/11/2015 1:31:01 PM ******/
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
	INNER JOIN Category ON Category.CategoryId = @CategoryId
	INNER JOIN UserCategory ON [User].UserId = UserCategory.UserId AND (UserCategory.CategoryId = @CategoryId OR UserCategory.CategoryId = Category.ParentCategoryId)
	ORDER BY FirstName, LastName, UserId

	-- add history event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The stored procedure to get admins for a certain category was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddHistoryEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime

END
