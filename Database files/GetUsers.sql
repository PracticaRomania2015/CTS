USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetUsers]    Script Date: 9/25/2015 10:11:37 AM ******/
DROP PROCEDURE [dbo].[GetUsers]
GO

/****** Object:  StoredProcedure [dbo].[GetUsers]    Script Date: 9/25/2015 10:11:37 AM ******/
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
	DECLARE @Action varchar(1000)
	DECLARE @DateTime datetime

	SET @TotalNumberOfUsers =0
	SELECT @Action = 'Requested all users for root panel; the stored procedure was successfully executed.'
	SELECT @DateTime = SYSDATETIME()

	SELECT @TotalNumberOfUsers = COUNT(*) FROM
	(
		SELECT UserId
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
	END;

	WITH Users AS
		(
			SELECT UserId, FirstName, LastName, Email, RoleName,
			ROW_NUMBER() OVER
			(
				ORDER BY 
					CASE WHEN @SortType = '' THEN UserId END ASC,
					CASE WHEN @SortType = 'UserId' AND @IsSearchASC = 1 THEN UserId END ASC,
					CASE WHEN @SortType = 'FirstName' AND @IsSearchASC = 1 THEN FirstName END ASC,
					CASE WHEN @SortType = 'LastName' AND @IsSearchASC = 1 THEN LastName END ASC,
					CASE WHEN @SortType = 'Email' AND @IsSearchASC = 1 THEN Email END ASC,
					CASE WHEN @SortType = 'Role' AND @IsSearchASC = 1 THEN RoleName END ASC,
					CASE WHEN @SortType = 'UserId' AND @IsSearchASC = 0 THEN UserId END DESC,
					CASE WHEN @SortType = 'FirstName' AND @IsSearchASC = 0 THEN FirstName END DESC,
					CASE WHEN @SortType = 'LastName' AND @IsSearchASC = 0 THEN LastName END DESC,
					CASE WHEN @SortType = 'Email' AND @IsSearchASC = 0 THEN Email END DESC,
					CASE WHEN @SortType = 'Role' AND @IsSearchASC = 0 THEN RoleName END DESC
			) AS RowNumber
			FROM [User]
			INNER JOIN Role ON [User].RoleId = Role.RoleId
			WHERE (@TextToSearch = '')
				OR (@SearchType = 'UserId' AND UserId like '%' + @TextToSearch + '%')
				OR (@SearchType = 'FirstName' AND FirstName like '%' + @TextToSearch + '%')
				OR (@SearchType = 'LastName' AND LastName like '%' + @TextToSearch + '%')
				OR (@SearchType = 'Email' AND Email like '%' + @TextToSearch + '%')
				OR (@SearchType = 'Role' AND RoleName like '%' + @TextToSearch + '%')
			GROUP BY UserId, FirstName, LastName, Email, RoleName
		)
		SELECT UserId, FirstName, LastName, Email, RoleName
		FROM Users
		WHERE RowNumber BETWEEN ((@RequestedPageNumber-1)*@UsersPerPage+1) AND (@RequestedPageNumber*@UsersPerPage)				

	-- add a new audit event
	EXEC dbo.AddAuditEvent 
	@UserId = NULL,
	@Action = @Action, 
	@DateTime = @DateTime,
	@TicketId = NULL
END

GO

