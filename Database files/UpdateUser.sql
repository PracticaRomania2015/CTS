USE [CTS]
GO
/****** Object:  StoredProcedure [dbo].[UpdateUser]    Script Date: 9/18/2015 3:09:22 PM ******/
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
		-- add a new audit event
		SELECT @Action = 'Updated personal details.'
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddAuditEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = NULL
	END
	ELSE
	BEGIN
		-- add a new audit event
		SELECT @Action = 'Failed to update personal details. The password is not valid.'
		SELECT @DateTime = SYSDATETIME()

		EXEC dbo.AddAuditEvent 
		@UserId = @UserId,
		@Action = @Action, 
		@DateTime = @DateTime,
		@TicketId = NULL
	END

END
