USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetQuestions]    Script Date: 10/19/2015 3:21:03 PM ******/
DROP PROCEDURE [dbo].[GetQuestions]
GO

/****** Object:  StoredProcedure [dbo].[GetQuestions]    Script Date: 10/19/2015 3:21:03 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetQuestions]

AS
BEGIN

	SET NOCOUNT ON;
	
	SELECT QuestionId, Question
	FROM Question
	ORDER BY Question, QuestionId

END

GO

