USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetCategories]    Script Date: 9/14/2015 9:20:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetCategories]
	@UserId int

AS
BEGIN
	
	SET NOCOUNT ON;

	SELECT Category.CategoryId, Category.CategoryName, Category.ParentCategoryId
	FROM Category
	LEFT JOIN UserCategory ON UserCategory.CategoryId = Category.CategoryId AND UserCategory.UserId = @UserId
	WHERE Category.ParentCategoryId IS NULL AND UserCategory.UserId IS NULL
	ORDER BY Category.CategoryName

	-- add history event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The stored procedure to get categories was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddHistoryEvent 
	@UserId = @UserId,
	@Action = @Action, 
	@DateTime = @DateTime

END