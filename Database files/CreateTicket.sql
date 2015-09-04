USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[CreateTicket]    Script Date: 9/4/2015 12:08:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[CreateTicket]
	@Subject varchar(50),
	@CategoryId int,
	@DateTime datetime,
	@Comment varchar(250),
	@UserId int,
	@FilePath varchar(100),
	@TicketId int OUTPUT,
	@CommentId int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	IF (@UserId != 0)
	BEGIN

		DECLARE @StateId int
		SELECT @StateId = StateId FROM State WHERE StateName = 'Active'

		INSERT INTO Ticket(Subject, CategoryId, StateId)
		VALUES (@Subject, @CategoryId, @StateId)

		SELECT TOP 1 @TicketId = TicketId FROM Ticket ORDER BY TicketId DESC

		INSERT INTO TicketComment(TicketId, DateTime, Comment, UserId, FilePath)
		VALUES (@TicketId, @DateTime, @Comment, @UserId, @FilePath)

		SELECT TOP 1 @CommentId = CommentId FROM TicketComment ORDER BY CommentId DESC

	END
	ELSE
	BEGIN
		
		INSERT INTO TicketComment(TicketId, DateTime, Comment, UserId, FilePath)
		VALUES (@TicketId, @DateTime, @Comment, @UserId, @FilePath)

	END
END
