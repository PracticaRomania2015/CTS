USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[AddCategory]    Script Date: 9/18/2015 3:04:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[AddCategory]
	@CategoryName varchar(50),
	@ParentCategoryId int

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(50)
	DECLARE @DateTime datetime

	IF (@ParentCategoryId = 0)
	BEGIN
		INSERT INTO Category (CategoryName, ParentCategoryId)
		VALUES (@CategoryName, NULL)

		-- add a new audit event
		SELECT @Action = 'A new category was added: ' + @CategoryName
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddAuditEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = NULL
	END
	ELSE
	BEGIN
		INSERT INTO Category (CategoryName, ParentCategoryId)
		VALUES (@CategoryName, @ParentCategoryId)

		-- add a new audit event
		SELECT @Action = 'A new subcategory was added for the category ' + (SELECT CategoryName FROM Category WHERE CategoryId = @ParentCategoryId) + ': ' + @CategoryName
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddAuditEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = NULL
	END
END
