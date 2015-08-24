package com.cts.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public abstract class BaseDAO {

	protected CallableStatement callableStatement;
	private static Connection connection;
	private List<InOutParam<?>> inOutParams = new ArrayList<InOutParam<?>>();

	private static SingleConnectionDataSource singleConnectionDataSource;

	static {

		// to be changed to relative path which doesn't work atm.. to me, at least
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"D:\\cts\\CTS\\Java files\\src\\main\\webapp\\WEB-INF\\spring\\appServlet\\servlet-context.xml");
		singleConnectionDataSource = (SingleConnectionDataSource) applicationContext.getBean("dataSource",
				SingleConnectionDataSource.class);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(singleConnectionDataSource);
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
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
