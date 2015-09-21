USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetUsers]    Script Date: 9/21/2015 12:53:35 PM ******/
DROP PROCEDURE [dbo].[GetUsers]
GO

/****** Object:  StoredProcedure [dbo].[GetUsers]    Script Date: 9/21/2015 12:53:35 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetUsers]
	@RequestedPageNumber int,
	@UsersPerPage int,
	@TextToSearch varchar(50),
	@SearchType varchar(50),
	@SortType varchar(50),
	@IsSearchASC bit,
	@TotalNumberOfPages int OUTPUT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @TotalNumberOfUsers INT
	DECLARE @UserTo INT
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SET @TotalNumberOfUsers =0
	SELECT @Action = 'Requested all users for root panel; the stored procedure was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	SELECT @TotalNumberOfUsers = COUNT(*) FROM
	(
		SELECT UserId, MAX(FirstName) AS FirstName, MAX(LastName) AS LastName, MAX(Email) AS Email
		FROM [User]
		WHERE (@TextToSearch = '')
			OR (@SearchType = 'UserId' AND UserId like '%' + @TextToSearch + '%')
			OR (@SearchType = 'FirstName' AND FirstName like '%' + @TextToSearch + '%')
			OR (@SearchType = 'LastName' AND LastName like '%' + @TextToSearch + '%')
			OR (@SearchType = 'Email' AND Email like '%' + @TextToSearch + '%')
		GROUP BY UserId
	)CountId

	IF @TotalNumberOfUsers % @UsersPerPage =0
	BEGIN
		SELECT @TotalNumberOfPages = @TotalNumberOfUsers/@UsersPerPage 
	END
	ELSE
	BEGIN
		SELECT @TotalNumberOfPages = @TotalNumberOfUsers/@UsersPerPage +1
	END

	IF @TotalNumberOfPages = 0
	BEGIN
		SET @TotalNumberOfPages=1
	END

	SELECT @UserTo = @RequestedPageNumber * @UsersPerPage

	IF (@UserTo > @TotalNumberOfUsers)
	BEGIN
		SELECT @UsersPerPage = @UsersPerPage - (@UserTo - @TotalNumberOfUsers)
	END

	SELECT * FROM (SELECT TOP (@UsersPerPage) * FROM (SELECT TOP (@UserTo) UserId, FirstName, LastName, Email
	FROM [User]
	WHERE (@TextToSearch = '')
		OR (@SearchType = 'UserId' AND UserId like '%' + @TextToSearch + '%')
		OR (@SearchType = 'FirstName' AND FirstName like '%' + @TextToSearch + '%')
		OR (@SearchType = 'LastName' AND LastName like '%' + @TextToSearch + '%')
		OR (@SearchType = 'Email' AND Email like '%' + @TextToSearch + '%')
	ORDER BY 
		CASE WHEN @SortType = '' THEN UserId END ASC,
		CASE WHEN @SortType = 'UserId' AND @IsSearchASC = 1 THEN UserId END ASC,
		CASE WHEN @SortType = 'FirstName' AND @IsSearchASC = 1 THEN FirstName END ASC,
		CASE WHEN @SortType = 'LastName' AND @IsSearchASC = 1 THEN LastName END ASC,
		CASE WHEN @SortType = 'Email' AND @IsSearchASC = 1 THEN Email END ASC,
		CASE WHEN @SortType = 'UserId' AND @IsSearchASC = 0 THEN UserId END DESC,
		CASE WHEN @SortType = 'FirstName' AND @IsSearchASC = 0 THEN FirstName END DESC,
		CASE WHEN @SortType = 'LastName' AND @IsSearchASC = 0 THEN LastName END DESC,
		CASE WHEN @SortType = 'Email' AND @IsSearchASC = 0 THEN Email END DESC
		) t ORDER BY
				CASE WHEN @SortType = '' THEN t.UserId END DESC,
				CASE WHEN @SortType = 'UserId' AND @IsSearchASC = 1 THEN t.UserId END DESC,
				CASE WHEN @SortType = 'FirstName' AND @IsSearchASC = 1 THEN t.FirstName END DESC,
				CASE WHEN @SortType = 'LastName' AND @IsSearchASC = 1 THEN t.LastName END DESC,
				CASE WHEN @SortType = 'Email' AND @IsSearchASC = 1 THEN t.Email END DESC,
				CASE WHEN @SortType = 'UserId' AND @IsSearchASC = 0 THEN t.UserId END ASC,
				CASE WHEN @SortType = 'FirstName' AND @IsSearchASC = 0 THEN t.FirstName END ASC,
				CASE WHEN @SortType = 'LastName' AND @IsSearchASC = 0 THEN t.LastName END ASC,
				CASE WHEN @SortType = 'Email' AND @IsSearchASC = 0 THEN t.Email END ASC
				) tt ORDER BY
						CASE WHEN @SortType = '' THEN tt.UserId END ASC,
						CASE WHEN @SortType = 'UserId' AND @IsSearchASC = 1 THEN tt.UserId END ASC,
						CASE WHEN @SortType = 'FirstName' AND @IsSearchASC = 1 THEN tt.FirstName END ASC,
						CASE WHEN @SortType = 'LastName' AND @IsSearchASC = 1 THEN tt.LastName END ASC,
						CASE WHEN @SortType = 'Email' AND @IsSearchASC = 1 THEN tt.Email END ASC,
						CASE WHEN @SortType = 'UserId' AND @IsSearchASC = 0 THEN tt.UserId END DESC,
						CASE WHEN @SortType = 'FirstName' AND @IsSearchASC = 0 THEN tt.FirstName END DESC,
						CASE WHEN @SortType = 'LastName' AND @IsSearchASC = 0 THEN tt.LastName END DESC,
						CASE WHEN @SortType = 'Email' AND @IsSearchASC = 0 THEN tt.Email END DESC
				

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END

GO

