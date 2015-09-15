USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[AddCategory]    Script Date: 9/15/2015 12:47:52 PM ******/
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

	IF (@ParentCategoryId = 0)
	BEGIN
		INSERT INTO Category (CategoryName, ParentCategoryId)
		VALUES (@CategoryName, NULL)
	END
	ELSE
	BEGIN
		INSERT INTO Category (CategoryName, ParentCategoryId)
		VALUES (@CategoryName, @ParentCategoryId)
	END

END
