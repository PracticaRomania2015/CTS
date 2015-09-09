USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[UpdateUser]    Script Date: 9/9/2015 9:53:46 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[UpdateUser]
	@UserId int,
	@FirstName varchar(50),
	@LastName varchar(50),
	@Title varchar(10),
	@NewPassword varchar(MAX),
	@OldPassword varchar(MAX),
	@Error int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
		
	SELECT @Error = 1

	UPDATE [User]
	SET FirstName = @FirstName, LastName = @LastName, Title = @Title, Password = @NewPassword, @Error = 0
	WHERE UserId = @UserId AND Password = @OldPassword

	IF (@Error = 0)
	BEGIN
		-- add history event
		SELECT @Action = 'Updated personal details.'
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddHistoryEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime
	END
	ELSE
	BEGIN
		-- add history event
		SELECT @Action = 'Failed to update personal details. The password is not valid.'
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddHistoryEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime
	END

END
