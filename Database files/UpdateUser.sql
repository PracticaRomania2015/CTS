USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[UpdateUser]    Script Date: 10/8/2015 1:21:23 PM ******/
DROP PROCEDURE [dbo].[UpdateUser]
GO

/****** Object:  StoredProcedure [dbo].[UpdateUser]    Script Date: 10/8/2015 1:21:23 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[UpdateUser]
	@UserId int,
	@FirstName varchar(50),
	@LastName varchar(50),
	@Title varchar(10),
	@NewPassword varchar(MAX),
	@OldPassword varchar(MAX),
	@ErrCode int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime
	
	SELECT @DateTime = SYSDATETIME()		
	SELECT @Action = 'Updated personal details.'

	SELECT @ErrCode = 1

	UPDATE [User]
	SET FirstName = @FirstName, LastName = @LastName, Title = @Title, Password = @NewPassword, @ErrCode = 0
	WHERE UserId = @UserId AND Password = @OldPassword

	IF (@ErrCode = 1)
	BEGIN
		-- if the update was not successfully then set an error message for audit event
		SELECT @Action = 'Failed to update personal details. The password is not valid.'		
	END

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = @UserId,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END

GO

