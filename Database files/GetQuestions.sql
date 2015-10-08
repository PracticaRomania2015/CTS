USE [CTS]
GO

/****** Object:  StoredProcedure [dbo].[GetQuestions]    Script Date: 10/8/2015 1:20:00 PM ******/
DROP PROCEDURE [dbo].[GetQuestions]
GO

/****** Object:  StoredProcedure [dbo].[GetQuestions]    Script Date: 10/8/2015 1:20:00 PM ******/
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

