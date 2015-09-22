USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[DeleteCategory]    Script Date: 9/22/2015 12:23:51 PM ******/
DROP PROCEDURE [dbo].[DeleteCategory]
GO

/****** Object:  StoredProcedure [dbo].[DeleteCategory]    Script Date: 9/22/2015 12:23:51 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[DeleteCategory]
	@CategoryId int

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(50)
	DECLARE @DateTime datetime
	SELECT @DateTime = SYSDATETIME()

	DECLARE @CategoryName varchar(50)
	SELECT @CategoryName = CategoryName FROM Category WHERE CategoryId = @CategoryId

	UPDATE Category
	SET IsActive = 0, CategoryName = CategoryName + '_' +  CONVERT(VARCHAR(19), @DateTime)
	WHERE ParentCategoryId = @CategoryId OR CategoryId = @CategoryId

	-- add a new audit event
	SELECT @Action = 'A category was deactivated: ' + @CategoryName

	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END

GO

