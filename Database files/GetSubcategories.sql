USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetSubcategories]    Script Date: 9/4/2015 12:09:21 PM ******/
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

END
