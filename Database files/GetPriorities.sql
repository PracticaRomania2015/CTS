USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetPriorities]    Script Date: 9/9/2015 10:31:46 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetPriorities]

AS
BEGIN

	SET NOCOUNT ON;
	SELECT PriorityId, PriorityName
	FROM Priority

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	SELECT @DateTime = SYSDATETIME()
	-- add history event
	SELECT @Action = 'The stored procedure to get priorities was successfully executed.'

	EXEC dbo.AddHistoryEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime

END
