USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[CreateTicket]    Script Date: 9/21/2015 12:51:57 PM ******/
DROP PROCEDURE [dbo].[CreateTicket]
GO

/****** Object:  StoredProcedure [dbo].[CreateTicket]    Script Date: 9/21/2015 12:51:57 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[CreateTicket]
	@Subject varchar(50),
	@CategoryId int,
	@DateTime datetime,
	@Comment varchar(250),
	@UserId int,
	@FilePath varchar(100),
	@PriorityId int,
	@TicketId int OUTPUT,
	@CommentId int OUTPUT,
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @Action varchar(1000)
	DECLARE @OldValue varchar(50)
	DECLARE @NewValue varchar(50)

	SET @ErrCode = 0

	-- *****
	-- to be modified; this is hard coded to normal priority until the ui is ready
	-- *****
	SELECT @PriorityId = 2
	-- *****

	DECLARE @StateId int
	SELECT @StateId = StateId FROM State WHERE StateName = 'Active'

	INSERT INTO Ticket(Subject, CategoryId, StateId, PriorityId)
	VALUES (@Subject, @CategoryId, @StateId, @PriorityId)

	IF (@@ROWCOUNT = 0 OR @@ERROR <> 0)
	BEGIN
		SET @ErrCode = 1
		RETURN
	END

	SELECT @TicketId = @@IDENTITY

	INSERT INTO TicketComment(TicketId, DateTime, Comment, UserId, FilePath)
	VALUES (@TicketId, @DateTime, @Comment, @UserId, @FilePath)

	IF (@@ROWCOUNT = 0 OR @@ERROR <> 0)
	BEGIN
		SET @ErrCode = 1
		RETURN
	END

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

GO

