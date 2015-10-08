USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[ChangeTicketCategory]    Script Date: 10/8/2015 1:17:54 PM ******/
DROP PROCEDURE [dbo].[ChangeTicketCategory]
GO

/****** Object:  StoredProcedure [dbo].[ChangeTicketCategory]    Script Date: 10/8/2015 1:17:54 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ChangeTicketCategory]
	@TicketId int,
	@CategoryId int,
	@UserId int,
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	DECLARE @OldValue varchar(50)
	DECLARE @NewValue varchar(50)

	SELECT @DateTime = SYSDATETIME()
	SELECT @Action = 'Change category'
	SELECT @OldValue = (SELECT Category.CategoryName FROM Ticket INNER JOIN Category ON Ticket.CategoryId = Category.CategoryId WHERE Ticket.TicketId = @TicketId)
	SELECT @NewValue = (SELECT CategoryName FROM Category WHERE CategoryId = @CategoryId)
	
	SELECT @ErrCode = 1

	-- change the category
	UPDATE Ticket
	SET CategoryId = @CategoryId, @ErrCode = 0
	WHERE TicketId = @TicketId

	IF (@ErrCode = 1)
	BEGIN
		-- if the category was not changed then change the new value for history event into old value
		SELECT @NewValue = @OldValue
	END

	-- add a new ticket history event
	EXEC dbo.AddTicketHistoryEvent 
	@TicketId = @TicketId,
	@UserId = @UserId,
	@DateTime = @DateTime,
	@Action = @Action,
	@OldValue = @OldValue,
	@NewValue = @NewValue

END

GO

