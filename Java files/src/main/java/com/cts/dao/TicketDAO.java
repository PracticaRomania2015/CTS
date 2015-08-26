package com.cts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.cts.entities.Category;
import com.cts.entities.TicketComment;
import com.cts.entities.Ticket;
import com.cts.entities.User;

public class TicketDAO extends BaseDAO implements TicketDAOInterface {

	@Override
	public boolean createTicket(Ticket ticket) {

		try {

			InOutParam<String> subjectParam = new InOutParam<String>(ticket.getSubject(), "Subject");
			InOutParam<Integer> categoryParam = new InOutParam<Integer>(ticket.getCategoryId(), "CategoryId");
			InOutParam<Timestamp> timestampParam = new InOutParam<Timestamp>(ticket.getComments().get(0).getDateTime(),
					"DateTime");
			InOutParam<String> commentParam = new InOutParam<String>(ticket.getComments().get(0).getComment(),
					"Comment");
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(ticket.getComments().get(0).getUserId(),
					"UserId");
			InOutParam<String> filePathParam = new InOutParam<String>(ticket.getComments().get(0).getFilePath(),
					"FilePath");
			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(0, "TicketId", true);
			InOutParam<Integer> commentIdParam = new InOutParam<Integer>(0, "CommentId", true);
			prepareExecution(StoredProceduresNames.CreateTicket, subjectParam, categoryParam, timestampParam,
					commentParam, userIdParam, filePathParam, ticketIdParam, commentIdParam);
			execute();
			ticket.setTicketId(ticketIdParam.getParameter());
			ticket.getComments().get(0).setTicketId(ticketIdParam.getParameter());
			ticket.getComments().get(0).setCommentId(commentIdParam.getParameter());
			ticket.getComments().get(0).setUserId(userIdParam.getParameter());
		} catch (SQLException e) {

			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}

	@Override
	public boolean deleteTicket(Ticket ticket) {

		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(0, "TicketId");
			prepareExecution(StoredProceduresNames.DeleteTicket, ticketIdParam);
			execute();
		} catch (SQLException e) {

			return false;
		} finally {

			closeCallableStatement();
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
		} catch (SQLException e) {

			return null;
		} finally {

			closeCallableStatement();
		}
		return categories;
	}

	@Override
	public ArrayList<Ticket> getTickets(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<TicketComment> getTicketComments(Ticket ticket) {

		ArrayList<TicketComment> ticketComments = new ArrayList<TicketComment>();
		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
			prepareExecution(StoredProceduresNames.GetTicketComments, ticketIdParam);
			ResultSet resultSet = execute();
			while (resultSet.next()) {

				TicketComment ticketComment = new TicketComment();
				ticketComment.setCommentId(resultSet.getInt(1));
				ticketComment.setTicketId(ticket.getTicketId());
				ticketComment.setDateTime(resultSet.getTimestamp(2));
				ticketComment.setComment(resultSet.getString(3));
				ticketComment.setUserId(resultSet.getInt(4));
				ticketComment.setFilePath(resultSet.getString(5));
				ticketComments.add(ticketComment);
			}
		} catch (SQLException e) {

			return null;
		} finally {

			closeCallableStatement();
		}
		return ticketComments;
	}

	@Override
	public boolean addCommentToTicket(Ticket ticket) {

		try {

			InOutParam<Integer> ticketCommentIdParam = new InOutParam<Integer>(0, "CommentId", true);
			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getNewTicketComment().getTicketId(),
					"TicketId");
			InOutParam<Timestamp> dateTimeParam = new InOutParam<Timestamp>(ticket.getNewTicketComment().getDateTime(),
					"DateTime");
			InOutParam<String> commentParam = new InOutParam<String>(ticket.getNewTicketComment().getComment(),
					"Comment");
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(ticket.getNewTicketComment().getUserId(),
					"UserId");
			InOutParam<String> filePathParam = new InOutParam<String>(ticket.getNewTicketComment().getFilePath(),
					"FilePath");
			InOutParam<Integer> errorParam = new InOutParam<Integer>(0, "Error", true);
			prepareExecution(StoredProceduresNames.AddCommentToTicket, ticketCommentIdParam, ticketIdParam, dateTimeParam, commentParam,
					userIdParam, filePathParam, errorParam);
			execute();
			if (errorParam.getParameter() == 0) {

				ArrayList<TicketComment> ticketComments = ticket.getComments();
				ticket.getNewTicketComment().setCommentId(ticketCommentIdParam.getParameter());
				ticketComments.add(ticket.getNewTicketComment());
				ticket.setComments(ticketComments);
				return true;
			} else {

				return false;
			}

		} catch (SQLException e) {

			return false;
		} finally {

			closeCallableStatement();
		}
	}
}
