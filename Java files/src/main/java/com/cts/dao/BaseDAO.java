package com.cts.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	
	protected ResultSet execute(boolean bothOutputParamsAndResultSet) throws SQLException{
		
		callableStatement.execute();
		return callableStatement.getResultSet();
	}

	private void setParameters() throws SQLException {

		if (inOutParams.size() != 0) {

			for (int i = 0; i < inOutParams.size(); i++) {

				if (inOutParams.get(i).isOutPutParam()) {

					callableStatement.registerOutParameter(inOutParams.get(i).getName(), inOutParams.get(i).getType());
				} else {

					switch (inOutParams.get(i).getType()) {

					case java.sql.Types.VARCHAR:
						callableStatement.setString(inOutParams.get(i).getName(),
								(String) inOutParams.get(i).getParameter());
						break;
					case java.sql.Types.INTEGER:
						callableStatement.setInt(inOutParams.get(i).getName(),
								(Integer) inOutParams.get(i).getParameter());
						break;
					case java.sql.Types.FLOAT:
						callableStatement.setFloat(inOutParams.get(i).getName(),
								(Float) inOutParams.get(i).getParameter());
						break;
					case java.sql.Types.DOUBLE:
						callableStatement.setDouble(inOutParams.get(i).getName(),
								(Double) inOutParams.get(i).getParameter());
						break;
					case java.sql.Types.BOOLEAN:
						callableStatement.setBoolean(inOutParams.get(i).getName(),
								(Boolean) inOutParams.get(i).getParameter());
						break;
					case java.sql.Types.TIMESTAMP:
						callableStatement.setTimestamp(inOutParams.get(i).getName(),
								(Timestamp) inOutParams.get(i).getParameter());
						break;
					default:
					}
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

				switch (inOutParams.get(i).getType()) {

				case java.sql.Types.VARCHAR:
					InOutParam<String> tempStringParam = (InOutParam<String>) inOutParams.get(i);
					tempStringParam.setParameter(callableStatement.getString(tempStringParam.getName()));
					break;
				case java.sql.Types.INTEGER:
					InOutParam<Integer> tempIntegerParam = (InOutParam<Integer>) inOutParams.get(i);
					tempIntegerParam.setParameter(callableStatement.getInt(tempIntegerParam.getName()));
					break;
				case java.sql.Types.FLOAT:
					InOutParam<Float> tempFloatParam = (InOutParam<Float>) inOutParams.get(i);
					tempFloatParam.setParameter(callableStatement.getFloat(tempFloatParam.getName()));
					break;
				case java.sql.Types.DOUBLE:
					InOutParam<Double> tempDoubleParam = (InOutParam<Double>) inOutParams.get(i);
					tempDoubleParam.setParameter(callableStatement.getDouble(tempDoubleParam.getName()));
					break;
				case java.sql.Types.BOOLEAN:
					InOutParam<Boolean> tempBooleanParam = (InOutParam<Boolean>) inOutParams.get(i);
					tempBooleanParam.setParameter(callableStatement.getBoolean(tempBooleanParam.getName()));
					break;
				case java.sql.Types.TIMESTAMP:
					InOutParam<Timestamp> tempTimestampParam = (InOutParam<Timestamp>) inOutParams.get(i);
					tempTimestampParam.setParameter(callableStatement.getTimestamp(tempTimestampParam.getName()));
					break;
				default:
				}
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
