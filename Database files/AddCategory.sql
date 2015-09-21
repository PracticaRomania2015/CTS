USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[AddCategory]    Script Date: 9/21/2015 12:51:16 PM ******/
DROP PROCEDURE [dbo].[AddCategory]
GO

/****** Object:  StoredProcedure [dbo].[AddCategory]    Script Date: 9/21/2015 12:51:16 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AddCategory]
	@CategoryName varchar(50),
	@ParentCategoryId int,
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(50)
	DECLARE @DateTime datetime

	SELECT @DateTime = SYSDATETIME()

	SET @ErrCode = 0

	SELECT @ErrCode = 1
	FROM Category
	WHERE CategoryName = @CategoryName

	IF (@ErrCode = 1)
	BEGIN
		-- category already exists
		-- check if is inactive
		-- if it is then make it active
		-- otherwise return error

		DECLARE @Check int = 0
		DECLARE @CategoryId int

		SELECT @CategoryId = CategoryId
		FROM Category
		WHERE CategoryName = @CategoryName

		UPDATE Category
		SET IsActive = 1, @Check = 1
		WHERE (CategoryId = @CategoryId OR ParentCategoryId = @CategoryId) AND IsActive = 0

		IF (@Check = 0)
		BEGIN
			SELECT @Action = 'Failed to add category or subcategory: ' + @CategoryName + '; a category with the same name already exists.'
		END
		ELSE
		BEGIN
			SET @ErrCode = 0
			SELECT @Action = 'Activate the category or subcategory: ' + @CategoryName
		END
	END
	ELSE
	BEGIN
		IF (@ParentCategoryId = 0)
		BEGIN
			INSERT INTO Category (CategoryName, ParentCategoryId)
			VALUES (@CategoryName, NULL)

			IF (@@ROWCOUNT = 0 OR @@ERROR <> 0)
			BEGIN
				-- error
				SET @ErrCode = 1
				SELECT @Action = 'Failed to add the category: ' + @CategoryName
			END
			ELSE
			BEGIN
				-- set param for audit event
				SELECT @Action = 'A new category was added: ' + @CategoryName
			END
		END
		ELSE
		BEGIN
			INSERT INTO Category (CategoryName, ParentCategoryId)
			VALUES (@CategoryName, @ParentCategoryId)

			IF (@@ROWCOUNT = 0 OR @@ERROR <> 0)
			BEGIN
				-- error
				SET @ErrCode = 1
				SELECT @Action = 'Failed to add the subcategory: ' + @CategoryName
			END
			ELSE
			BEGIN
				-- set param for audit event
				SELECT @Action = 'A new subcategory was added for the category ' + (SELECT CategoryName FROM Category WHERE CategoryId = @ParentCategoryId) + ': ' + @CategoryName
			END
		END
	END

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END

GO

