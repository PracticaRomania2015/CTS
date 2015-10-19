USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[EditCategory]    Script Date: 10/19/2015 3:19:27 PM ******/
DROP PROCEDURE [dbo].[EditCategory]
GO

/****** Object:  StoredProcedure [dbo].[EditCategory]    Script Date: 10/19/2015 3:19:27 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[EditCategory]
	@CategoryId INT,
	@CategoryName VARCHAR(255),
	@ErrCode INT OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(50)
	DECLARE @DateTime datetime

	SELECT @DateTime = SYSDATETIME()
	SET @Action = 'The category name was changed; Old value: ' + (SELECT CategoryName FROM Category WHERE CategoryId = @CategoryId) + '; New value: ' + @CategoryName
	SET @ErrCode = 1

	UPDATE Category
	SET CategoryName = @CategoryName, @ErrCode = 0
	WHERE CategoryId = @CategoryId

	IF (@ErrCode = 1)
	BEGIN
		SET @Action = 'Failed to change the name of category caused by bad category id.'
	END

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END

GO

