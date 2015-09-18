USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[DeleteCategory]    Script Date: 9/18/2015 3:06:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[DeleteCategory]
	@CategoryId int

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @CategoryName varchar(50)
	SELECT @CategoryName = CategoryName FROM Category WHERE CategoryId = @CategoryId

	DELETE FROM Category
	WHERE ParentCategoryId = @CategoryId
	
	DELETE FROM Category
	WHERE CategoryId = @CategoryId

	-- add a new audit event
	DECLARE @Action varchar(50)
	DECLARE @DateTime datetime
	SELECT @Action = 'A category was deleted: ' + @CategoryName
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END
