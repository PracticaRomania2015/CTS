USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[DeleteCategory]    Script Date: 9/15/2015 12:47:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[DeleteCategory]
	@CategoryId int

AS
BEGIN

	SET NOCOUNT ON;

	DELETE FROM Category
	WHERE ParentCategoryId = @CategoryId
	
	DELETE FROM Category
	WHERE CategoryId = @CategoryId
END
