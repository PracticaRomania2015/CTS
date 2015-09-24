USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetCategoriesRightsForUser]    Script Date: 9/23/2015 11:46:58 AM ******/
DROP PROCEDURE [dbo].[GetCategoriesRightsForUser]
GO

/****** Object:  StoredProcedure [dbo].[GetCategoriesRightsForUser]    Script Date: 9/23/2015 11:46:58 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetCategoriesRightsForUser]
	@UserId int,
	@IsSysAdmin bit OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @RoleName varchar(50)
	SELECT @RoleName = RoleName FROM [User] INNER JOIN Role ON [User].RoleId = Role.RoleId WHERE [User].UserId = @UserId

	IF (@RoleName = 'SysAdmin')
	BEGIN
		-- the user is sys admin
		SELECT @IsSysAdmin = 1
	END
	ELSE
	BEGIN
		-- the user is not sys admin
		SELECT @IsSysAdmin = 0
	END

	SELECT Category.CategoryId, Category.CategoryName, UserCategory.UserId AS CategoryUserId
	FROM [User]
	INNER JOIN UserCategory ON [User].UserId = UserCategory.UserId AND [User].UserId = @UserId
	RIGHT JOIN Category ON UserCategory.CategoryId = Category.CategoryId
	WHERE Category.ParentCategoryId IS NULL AND Category.IsActive = 1
	ORDER BY Category.CategoryName


	-- add a new audit event
	DECLARE @Action varchar(50)
	DECLARE @DateTime datetime
	SELECT @Action = 'The stored procedure to get categories rights for user was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL

END

GO

