USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[CreateTicket]    Script Date: 9/8/2015 12:04:30 PM ******/
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
	DECLARE @Action varchar(1000)

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

		-- add a new history event
		SELECT @Action = 'Create a new ticket'

		EXEC dbo.AddHistoryEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime

	END
	ELSE
	BEGIN
		
		INSERT INTO TicketComment(TicketId, DateTime, Comment, UserId, FilePath)
		VALUES (@TicketId, @DateTime, @Comment, @UserId, @FilePath)

		-- add a new history event
		SELECT @Action = 'Failed to create a new ticket caused by UserId = 0.'

		EXEC dbo.AddHistoryEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime

	END
END
