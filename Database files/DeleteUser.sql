USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[DeleteUser]    Script Date: 9/8/2015 12:05:43 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[DeleteUser]
	@UserId int

AS
BEGIN

	SET NOCOUNT ON;

	DELETE FROM [User] WHERE UserId = @UserId

	-- add history event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The user [UserId = ' + CONVERT(VARCHAR(25), @UserId, 126) + '] was deleted'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddHistoryEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime

END
