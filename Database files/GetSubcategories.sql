USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetSubcategories]    Script Date: 9/8/2015 12:05:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetSubcategories]
	@CategoryId int

AS
BEGIN
	
	SET NOCOUNT ON;

	SELECT CategoryId, CategoryName
	FROM Category
	WHERE ParentCategoryId = @CategoryId
	ORDER BY CategoryName

	-- add history event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The stored procedure to get subcategories for the category [CategoryId = ' + @CategoryId + '] was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddHistoryEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime

END
