USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[AddAuditEvent]    Script Date: 10/8/2015 1:17:10 PM ******/
DROP PROCEDURE [dbo].[AddAuditEvent]
GO

/****** Object:  StoredProcedure [dbo].[AddAuditEvent]    Script Date: 10/8/2015 1:17:10 PM ******/
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

