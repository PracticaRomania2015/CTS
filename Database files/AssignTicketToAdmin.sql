USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[AssignTicketToAdmin]    Script Date: 9/4/2015 12:07:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[AssignTicketToAdmin]
	@TicketId int,
	@UserId int

AS
BEGIN

	SET NOCOUNT ON;

	IF (@UserId = 0)
		BEGIN
			UPDATE Ticket
			SET AssignedToUserId = NULL
			WHERE TicketId = @TicketId
		END
	ELSE
		BEGIN
			UPDATE Ticket
			SET AssignedToUserId = @UserId
			WHERE TicketId = @TicketId
		END
END
