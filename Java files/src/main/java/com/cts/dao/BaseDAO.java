package com.cts.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public abstract class BaseDAO {

	private CallableStatement callableStatement;
	private static Connection connection;
	private List<InOutParam<?>> inOutParams = new ArrayList<InOutParam<?>>();
	private static SingleConnectionDataSource singleConnectionDataSource;

	static {

		makeConnection();
	}

	private static void makeConnection() {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("servlet-context.xml");
		singleConnectionDataSource = (SingleConnectionDataSource) applicationContext.getBean("dataSource",
				SingleConnectionDataSource.class);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(singleConnectionDataSource);
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
		}
	}

	protected void prepareExecution(StoredProceduresNames storedProcedureName, InOutParam<?>... parameters)
			throws SQLException {

		// if the connection is not valid then remake the connection
		if (!connection.isValid(0)) {
			makeConnection();
		}
		inOutParams.clear();
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				inOutParams.add(parameters[i]);
			}
		}
		callableStatement = connection.prepareCall(createSqlCall(storedProcedureName));
		setParameters();
	}

	protected ResultSet execute() throws SQLException {

		callableStatement.execute();
		setOutParametersAfterExecute();
		return callableStatement.getResultSet();
	}

	protected ResultSet execute(boolean bothOutputParamsAndResultSet) throws SQLException {

		ResultSet rs;
		callableStatement.execute();
		rs = callableStatement.getResultSet();
		return rs;
	}

	private void setParameters() throws SQLException {

		if (inOutParams.size() != 0) {
			for (int i = 0; i < inOutParams.size(); i++) {
				if (inOutParams.get(i).isOutPutParam()) {
					callableStatement.registerOutParameter(inOutParams.get(i).getName(), inOutParams.get(i).getType());
				} else {
					callableStatement.setObject(inOutParams.get(i).getName(), inOutParams.get(i).getParameter());
				}
			}
		}
	}

	private String createSqlCall(StoredProceduresNames storedProcedureName) {

		StringBuffer sqlCall;
		if (inOutParams.size() == 0) {
			sqlCall = new StringBuffer("{call " + storedProcedureName + "}");
		} else {
			sqlCall = new StringBuffer("{call " + storedProcedureName + "(");
			for (int i = 0; i < inOutParams.size(); i++) {
				if (inOutParams.size() == 1) {
					sqlCall.append("?)}");
				} else if (i == 0) {
					sqlCall.append("?");
				} else if (i == inOutParams.size() - 1) {
					sqlCall.append(", ?)}");
				} else {
					sqlCall.append(", ?");
				}
			}
		}
		return sqlCall.toString();
	}

	@SuppressWarnings("unchecked")
	protected void setOutParametersAfterExecute() throws SQLException {

		for (int i = 0; i < inOutParams.size(); i++) {
			if (inOutParams.get(i).isOutPutParam()) {
				InOutParam<Object> tempParam = (InOutParam<Object>) inOutParams.get(i);
				tempParam.setParameter(callableStatement.getObject(inOutParams.get(i).getName()));
			}
		}
	}

	protected void closeCallableStatement() {

		try {
			callableStatement.close();
		} catch (SQLException e) {
		}
	}
}