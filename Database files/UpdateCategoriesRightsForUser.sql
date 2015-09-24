USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[UpdateCategoriesRightsForUser]    Script Date: 9/24/2015 8:32:06 AM ******/
DROP PROCEDURE [dbo].[UpdateCategoriesRightsForUser]
GO

/****** Object:  StoredProcedure [dbo].[UpdateCategoriesRightsForUser]    Script Date: 9/24/2015 8:32:06 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[UpdateCategoriesRightsForUser]
	@UserId int,
	@IsSysAdmin bit,
	@CategoryIdList varchar(500),
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @DateTime = SYSDATETIME()
	SELECT @Action = 'Update categories rights for user.'

	SET @ErrCode = 0

	DECLARE @SQL varchar(600)

	-- remove categories from user
	SET @SQL = 
	'DELETE	FROM UserCategory
	WHERE UserId = ' + CONVERT(varchar(10), @UserId) + ' AND CategoryId NOT IN (' + @CategoryIdList + ') AND CategoryId IN (SELECT CategoryId FROM Category WHERE IsActive = 1)'
	EXEC(@SQL)

	-- add new categories for user
	SET @SQL = 
	'DECLARE @CategoryId int = 1
	 WHILE (@CategoryId <> 0)
	 BEGIN
		SET @CategoryId = 0
			
		SELECT TOP 1 @CategoryId = CategoryId FROM Category WHERE CategoryId IN (' + @CategoryIdList + ') AND CategoryId NOT IN (SELECT CategoryId FROM UserCategory WHERE UserId = ' + CONVERT(varchar(10), @UserId) + ')
			
		IF (@CategoryId <> 0)
			INSERT INTO UserCategory
			VALUES (' + CONVERT(varchar(10), @UserId) + ', @CategoryId)
	END'
	EXEC(@SQL)

	DECLARE @RoleId int

	-- change the role for user
	IF (@IsSysAdmin = 1)
	BEGIN
		-- is sys admin
		SELECT @RoleId = RoleId
		FROM Role
		WHERE RoleName = 'SysAdmin'
	END
	ELSE
	BEGIN
		IF (@UserId IN (SELECT UserId FROM UserCategory))
		BEGIN
			-- is admin
			SELECT @RoleId = RoleId
			FROM Role
			WHERE RoleName = 'Admin'
		END
		ELSE
		BEGIN
			-- is user
			SELECT @RoleId = RoleId
			FROM Role
			WHERE RoleName = 'User'
		END
	END

	UPDATE [User]
	SET RoleId = @RoleId
	WHERE UserId = @UserId

	IF (@@ROWCOUNT = 0 OR @@ERROR <> 0)
		SET @ErrCode = 1

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END

GO

