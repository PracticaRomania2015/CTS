USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetSubcategories]    Script Date: 10/19/2015 3:21:08 PM ******/
DROP PROCEDURE [dbo].[GetSubcategories]
GO

/****** Object:  StoredProcedure [dbo].[GetSubcategories]    Script Date: 10/19/2015 3:21:08 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetSubcategories]
	@CategoryId int

AS
BEGIN
	
	SET NOCOUNT ON;

	SELECT CategoryId, CategoryName
	FROM Category
	WHERE ParentCategoryId = @CategoryId AND IsActive = 1
	ORDER BY CategoryName

	-- add a new audit event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The stored procedure to get subcategories for the category ' + (SELECT CategoryName FROM Category WHERE CategoryId = @CategoryId) + ' was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL

END

GO

