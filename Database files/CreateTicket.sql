USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[CreateTicket]    Script Date: 9/18/2015 3:06:18 PM ******/
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
	@PriorityId int,
	@TicketId int OUTPUT,
	@CommentId int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @OldValue varchar(50)
	DECLARE @NewValue varchar(50)

	IF (@UserId != 0)
	BEGIN

		-- *****
		-- to be modified; this is hard coded to normal priority
		-- *****
		SELECT @PriorityId = 2
		-- *****

		DECLARE @StateId int
		SELECT @StateId = StateId FROM State WHERE StateName = 'Active'

		INSERT INTO Ticket(Subject, CategoryId, StateId, PriorityId)
		VALUES (@Subject, @CategoryId, @StateId, @PriorityId)

		SELECT TOP 1 @TicketId = TicketId FROM Ticket ORDER BY TicketId DESC

		INSERT INTO TicketComment(TicketId, DateTime, Comment, UserId, FilePath)
		VALUES (@TicketId, @DateTime, @Comment, @UserId, @FilePath)

		SELECT TOP 1 @CommentId = CommentId FROM TicketComment ORDER BY CommentId DESC

		-- add a new ticket history event
		SELECT @Action = 'Submit'
		SELECT @OldValue = ''
		SELECT @NewValue = ''

		EXEC dbo.AddTicketHistoryEvent 
		@TicketId = @TicketId,
		@UserId = @UserId,
		@DateTime = @DateTime,
		@Action = @Action,
		@OldValue = @OldValue,
		@NewValue = @NewValue

	END
	ELSE
	BEGIN
		
		INSERT INTO TicketComment(TicketId, DateTime, Comment, UserId, FilePath)
		VALUES (@TicketId, @DateTime, @Comment, @UserId, @FilePath)

		-- add a new audit event
		SELECT @Action = 'Failed to create a new ticket caused by UserId = 0.'

		EXEC dbo.AddAuditEvent 
		@UserId = NULL,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = NULL

	END
END
