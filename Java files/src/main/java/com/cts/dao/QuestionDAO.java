package com.cts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.cts.entities.Question;

public class QuestionDAO extends BaseDAO implements QuestionDAOInterface {

	@Override
	public ArrayList<Question> getQuestions() {

		ArrayList<Question> questions = new ArrayList<Question>();
		try {

			prepareExecution(StoredProceduresNames.GetQuestions);
			ResultSet resultSet = execute();
			while (resultSet.next()) {

				Question question = new Question();
				question.setQuestionId(resultSet.getInt("QuestionId"));
				question.setQuestion(resultSet.getString("Question"));
				questions.add(question);
			}
		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}
		return questions;
	}
}
