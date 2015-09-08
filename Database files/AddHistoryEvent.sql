USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[AddHistoryEvent]    Script Date: 9/8/2015 12:03:27 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[AddHistoryEvent]
	@UserId int,
	@Action varchar(1000),
	@DateTime datetime

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Username varchar(100)

	SELECT @Username = (FirstName + ' ' + LastName + ' [' + Email + ']') FROM [User] WHERE UserId = @UserId

	INSERT INTO History(Username, Action, Datetime)
	VALUES (@Username, @Action, @DateTime)

END
