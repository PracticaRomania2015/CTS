package com.cts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.cts.entities.Priority;

public class PriorityDAO extends BaseDAO implements PriorityDAOInterface {

	@Override
	public ArrayList<Priority> getPriorities() {

		ArrayList<Priority> priorities = new ArrayList<Priority>();
		try {
			prepareExecution(StoredProceduresNames.GetPriorities);
			ResultSet resultSet = execute();
			while (resultSet.next()) {
				Priority priority = new Priority();
				priority.setPriorityId(resultSet.getInt("PriorityId"));
				priority.setPriorityName(resultSet.getString("PriorityName"));
				priorities.add(priority);
			}
		} catch (SQLException e) {
		} finally {
			closeCallableStatement();
		}
		return priorities;
	}
}