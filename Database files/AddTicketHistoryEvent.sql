USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[AddTicketHistoryEvent]    Script Date: 10/8/2015 1:17:31 PM ******/
DROP PROCEDURE [dbo].[AddTicketHistoryEvent]
GO

/****** Object:  StoredProcedure [dbo].[AddTicketHistoryEvent]    Script Date: 10/8/2015 1:17:31 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AddTicketHistoryEvent] 
	@TicketId int,
	@UserId int,
	@DateTime datetime,
	@Action varchar(50),
	@OldValue varchar(50),
	@NewValue varchar(50)

AS
BEGIN

	SET NOCOUNT ON;

	INSERT INTO TicketsHistory (TicketId, UserId, DateTime, ActionId, OldValue, NewValue)
	VALUES (@TicketId, @UserId, @DateTime, (SELECT ActionId FROM Action WHERE ActionName = @Action), @OldValue, @NewValue)

END

GO

