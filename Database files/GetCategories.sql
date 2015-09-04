USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetCategories]    Script Date: 9/4/2015 12:09:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetCategories]

AS
BEGIN
	
	SET NOCOUNT ON;

	SELECT CategoryId, CategoryName, ParentCategoryId
	FROM Category
	WHERE ParentCategoryId IS NULL
	ORDER BY CategoryName

END