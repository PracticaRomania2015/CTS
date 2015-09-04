USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[GetTicketComments]    Script Date: 9/4/2015 12:09:29 PM ******/
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

END
