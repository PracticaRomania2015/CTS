USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetUsers]    Script Date: 9/24/2015 9:16:45 AM ******/
DROP PROCEDURE [dbo].[GetUsers]
GO

/****** Object:  StoredProcedure [dbo].[GetUsers]    Script Date: 9/24/2015 9:16:45 AM ******/
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
	END;



	WITH Users AS
		(
			SELECT UserId, MAX(FirstName) AS FirstName, MAX(LastName) AS LastName, MAX(Email) AS Email, MAX(RoleName) AS Role,
			ROW_NUMBER() OVER
			(
				ORDER BY 
					CASE WHEN @SortType = '' THEN UserId END ASC,
					CASE WHEN @SortType = 'UserId' AND @IsSearchASC = 1 THEN UserId END ASC,
					CASE WHEN @SortType = 'FirstName' AND @IsSearchASC = 1 THEN MAX(FirstName) END ASC,
					CASE WHEN @SortType = 'LastName' AND @IsSearchASC = 1 THEN MAX(LastName) END ASC,
					CASE WHEN @SortType = 'Email' AND @IsSearchASC = 1 THEN MAX(Email) END ASC,
					CASE WHEN @SortType = 'Role' AND @IsSearchASC = 1 THEN MAX(RoleName) END ASC,
					CASE WHEN @SortType = 'UserId' AND @IsSearchASC = 0 THEN UserId END DESC,
					CASE WHEN @SortType = 'FirstName' AND @IsSearchASC = 0 THEN MAX(FirstName) END DESC,
					CASE WHEN @SortType = 'LastName' AND @IsSearchASC = 0 THEN MAX(LastName) END DESC,
					CASE WHEN @SortType = 'Email' AND @IsSearchASC = 0 THEN MAX(Email) END DESC,
					CASE WHEN @SortType = 'Role' AND @IsSearchASC = 0 THEN MAX(RoleName) END DESC
			) AS RowNumber
			FROM [User]
			INNER JOIN Role ON [User].RoleId = Role.RoleId
			WHERE (@TextToSearch = '')
				OR (@SearchType = 'UserId' AND UserId like '%' + @TextToSearch + '%')
				OR (@SearchType = 'FirstName' AND FirstName like '%' + @TextToSearch + '%')
				OR (@SearchType = 'LastName' AND LastName like '%' + @TextToSearch + '%')
				OR (@SearchType = 'Email' AND Email like '%' + @TextToSearch + '%')
				OR (@SearchType = 'Role' AND RoleName like '%' + @TextToSearch + '%')
			GROUP BY UserId
		)
		SELECT UserId, FirstName, LastName, Email
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

