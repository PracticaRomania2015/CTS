USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetCategories]    Script Date: 9/21/2015 12:52:48 PM ******/
DROP PROCEDURE [dbo].[GetCategories]
GO

/****** Object:  StoredProcedure [dbo].[GetCategories]    Script Date: 9/21/2015 12:52:48 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetCategories]

AS
BEGIN
	
	SET NOCOUNT ON;

	SELECT Category.CategoryId, Category.CategoryName, Category.ParentCategoryId
	FROM Category
	WHERE Category.ParentCategoryId IS NULL AND IsActive = 1
	ORDER BY Category.CategoryName

	-- add a new audit event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The stored procedure to get categories was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL

END
GO

