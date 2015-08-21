package com.cts.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public abstract class BaseDAO {

	protected CallableStatement callableStatement;
	private static Connection connection;
	private List<InOutParam<?>> inOutParams = new ArrayList<InOutParam<?>>();

	static {

		SQLServerDataSource dataSource = new SQLServerDataSource();
		dataSource.setUser("sa");
		dataSource.setPassword("1234");
		dataSource.setServerName("192.168.250.176");
		dataSource.setPortNumber(1433);
		dataSource.setDatabaseName("CTS");
		try {

			connection = dataSource.getConnection();
		} catch (SQLServerException e) {
		}
	}

	public void prepareExecution(StoredProceduresNames storedProcedureName, InOutParam<?>... parameters)
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

	public ResultSet execute() throws SQLException {

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
}
