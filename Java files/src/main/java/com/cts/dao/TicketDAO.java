package com.cts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.cts.dao.BaseDAO;
import com.cts.dao.TicketDAOInterface;
import com.cts.entities.Category;
import com.cts.entities.Ticket;
import com.cts.entities.User;

public class TicketDAO extends BaseDAO implements TicketDAOInterface {

	@Override
	public boolean createTicket(Ticket ticket) {

		try {

			InOutParam<String> subjectParam = new InOutParam<String>(ticket.getSubject(), "Subject", false);
			InOutParam<String> categoryParam = new InOutParam<String>(ticket.getCategory(), "Category", false);
			InOutParam<Timestamp> timestampParam = new InOutParam<Timestamp>(ticket.getDateTime(), "DateTime", false);
			InOutParam<String> commentParam = new InOutParam<String>(ticket.getDescription(), "Comment", false);
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(ticket.getUserId(), "UserId", false);
			InOutParam<String> filePathParam = new InOutParam<String>(ticket.getFilePath(), "FilePath", false);
			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(0, "TicketId", true);
			prepareExecution(StoredProceduresNames.CreateTicket, subjectParam, categoryParam, timestampParam,
					commentParam, userIdParam, filePathParam, ticketIdParam);
			execute();
			ticket.setTicketId(callableStatement.getInt(ticketIdParam.getName()));
			callableStatement.close();
		} catch (SQLException e) {

			return false;
		}
		return true;
	}

	@Override
	public boolean deleteTicket(Ticket ticket) {

		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(0, "TicketId", false);
			prepareExecution(StoredProceduresNames.DeleteTicket, ticketIdParam);
			execute();
			callableStatement.close();
		} catch (SQLException e) {

			return false;
		}
		return true;
	}

	@Override
	public ArrayList<Category> getCategories() {

		ArrayList<Category> categories = new ArrayList<Category>();
		try {

			prepareExecution(StoredProceduresNames.GetAllCategories);
			ResultSet resultSet = execute();
			while (resultSet.next()) {

				Category category = new Category();
				category.setCategoryId(resultSet.getInt(1));
				category.setCategoryName(resultSet.getString(2));
				category.setParentCategoryId(resultSet.getInt(3));
				categories.add(category);
			}
			callableStatement.close();
		} catch (SQLException e) {

			return null;
		}
		return categories;
	}

	@Override
	public ArrayList<Ticket> getTickets(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}
