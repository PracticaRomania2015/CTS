USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetPriorities]    Script Date: 9/21/2015 12:53:09 PM ******/
DROP PROCEDURE [dbo].[GetPriorities]
GO

/****** Object:  StoredProcedure [dbo].[GetPriorities]    Script Date: 9/21/2015 12:53:09 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetPriorities]

AS
BEGIN

	SET NOCOUNT ON;
	SELECT PriorityId, PriorityName
	FROM Priority

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	SELECT @DateTime = SYSDATETIME()

	-- add a new audit event
	SELECT @Action = 'The stored procedure to get priorities was successfully executed.'

	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL

END

GO

