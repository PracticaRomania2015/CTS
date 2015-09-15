USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetCategories]    Script Date: 9/15/2015 9:47:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetCategories]

AS
BEGIN
	
	SET NOCOUNT ON;

	SELECT Category.CategoryId, Category.CategoryName, Category.ParentCategoryId
	FROM Category
	WHERE Category.ParentCategoryId IS NULL
	ORDER BY Category.CategoryName

	-- add history event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The stored procedure to get categories was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddHistoryEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime

END