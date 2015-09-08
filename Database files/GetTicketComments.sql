USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetTicketComments]    Script Date: 9/8/2015 12:05:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetTicketComments]
	@TicketId int

AS
BEGIN

	SET NOCOUNT ON;

	SELECT CommentId, DateTime, Comment, TicketComment.UserId, FirstName, LastName, FilePath
	FROM TicketComment
	INNER JOIN [User] ON TicketComment.UserId = [User].UserId
	WHERE TicketId = @TicketId
	ORDER BY DateTime

	-- add history event
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SELECT @Action = 'The stored procedure to get comments for the ticket [TicketId = ' + @TicketId + '] was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	EXEC dbo.AddHistoryEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime

END
