USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[AddAuditEvent]    Script Date: 10/19/2015 3:17:50 PM ******/
DROP PROCEDURE [dbo].[AddAuditEvent]
GO

/****** Object:  StoredProcedure [dbo].[AddAuditEvent]    Script Date: 10/19/2015 3:17:50 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[AddAuditEvent]
	@UserId int,
	@Action varchar(1000),
	@DateTime datetime,
	@TicketId int

AS
BEGIN

	SET NOCOUNT ON;

	INSERT INTO Audit(UserId, Action, Datetime, TicketId)
	VALUES (@UserId, @Action, @DateTime, @TicketId)

END

GO

