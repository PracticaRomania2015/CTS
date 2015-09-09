package com.cts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.cts.entities.Category;
import com.cts.entities.Priority;
import com.cts.entities.State;
import com.cts.entities.Ticket;
import com.cts.entities.TicketComment;
import com.cts.entities.User;
import com.cts.entities.ViewTicketsRequest;

public class TicketDAO extends BaseDAO implements TicketDAOInterface {

	@Override
	public boolean createTicket(Ticket ticket) {

		try {

			InOutParam<String> subjectParam = new InOutParam<String>(ticket.getSubject(), "Subject");
			InOutParam<Integer> categoryParam = new InOutParam<Integer>(ticket.getCategory().getCategoryId(),
					"CategoryId");
			InOutParam<Timestamp> timestampParam = new InOutParam<Timestamp>(ticket.getComments().get(0).getDateTime(),
					"DateTime");
			InOutParam<String> commentParam = new InOutParam<String>(ticket.getComments().get(0).getComment(),
					"Comment");
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(ticket.getComments().get(0).getUser().getUserId(),
					"UserId");
			InOutParam<String> filePathParam = new InOutParam<String>(ticket.getComments().get(0).getFilePath(),
					"FilePath");
			InOutParam<Integer> priorityIdParam = new InOutParam<Integer>(ticket.getPriority().getPriorityId(),
					"PriorityId");
			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(0, "TicketId", true);
			InOutParam<Integer> commentIdParam = new InOutParam<Integer>(0, "CommentId", true);
			prepareExecution(StoredProceduresNames.CreateTicket, subjectParam, categoryParam, timestampParam,
					commentParam, userIdParam, filePathParam, priorityIdParam, ticketIdParam, commentIdParam);
			execute();
			ticket.setTicketId(ticketIdParam.getParameter());
			ticket.getComments().get(0).setTicketId(ticketIdParam.getParameter());
			ticket.getComments().get(0).setCommentId(commentIdParam.getParameter());
			ticket.getComments().get(0).getUser().setUserId(userIdParam.getParameter());
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

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
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

			prepareExecution(StoredProceduresNames.GetCategories);
			ResultSet resultSet = execute();
			while (resultSet.next()) {

				Category category = new Category();
				category.setCategoryId(resultSet.getInt(1));
				category.setCategoryName(resultSet.getString(2));
				category.setParentCategoryId(resultSet.getInt(3));
				categories.add(category);
			}
		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}
		return categories;
	}

	@Override
	public ArrayList<Category> getSubcategories(Category category) {

		ArrayList<Category> subcategories = new ArrayList<Category>();
		try {

			InOutParam<Integer> categoryIdParam = new InOutParam<Integer>(category.getCategoryId(), "CategoryId");
			prepareExecution(StoredProceduresNames.GetSubcategories, categoryIdParam);
			ResultSet resultSet = execute();
			while (resultSet.next()) {

				Category subcategory = new Category();
				subcategory.setCategoryId(resultSet.getInt(1));
				subcategory.setCategoryName(resultSet.getString(2));
				subcategory.setParentCategoryId(category.getCategoryId());
				subcategories.add(subcategory);
			}
		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}

		return subcategories;
	}

	@Override
	public ArrayList<Ticket> getTickets(ViewTicketsRequest viewTicketsRequest, StringBuilder totalNumberOfPages) {

		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		try {

			InOutParam<Integer> userIdParam = new InOutParam<Integer>(viewTicketsRequest.getUser().getUserId(),
					"UserId");
			InOutParam<Integer> isViewMyTicketsRequestParam = new InOutParam<Integer>(
					viewTicketsRequest.getTypeOfRequest(), "IsViewMyTicketsRequest");
			InOutParam<Integer> requestedPageNumberParam = new InOutParam<Integer>(
					viewTicketsRequest.getRequestedPageNumber(), "RequestedPageNumber");
			InOutParam<Integer> ticketsPerPageParam = new InOutParam<Integer>(viewTicketsRequest.getTicketsPerPage(),
					"TicketsPerPage");
			InOutParam<String> textToSearchParam = new InOutParam<String>(viewTicketsRequest.getTextToSearch(),
					"TextToSearch");
			InOutParam<String> searchTypeParam = new InOutParam<String>(viewTicketsRequest.getSearchType(),
					"SearchType");
			InOutParam<Integer> totalNumberOfPagesParam = new InOutParam<Integer>(0, "TotalNumberOfPages", true);
			prepareExecution(StoredProceduresNames.GetTickets, userIdParam, isViewMyTicketsRequestParam,
					requestedPageNumberParam, ticketsPerPageParam, textToSearchParam, searchTypeParam,
					totalNumberOfPagesParam);
			ResultSet resultSet = execute(true);

			while (resultSet.next()) {

				Ticket ticket = new Ticket();
				ticket.setTicketId(resultSet.getInt(1));
				ticket.setSubject(resultSet.getString(2));
				TicketComment firstTicketComment = new TicketComment();
				firstTicketComment.setDateTime(resultSet.getTimestamp(3));
				ticket.getComments().add(firstTicketComment);
				Category category = new Category();
				category.setCategoryId(resultSet.getInt(4));
				category.setCategoryName(resultSet.getString(5));
				ticket.setCategory(category);
				State state = new State();
				state.setStateId(resultSet.getInt(6));
				state.setStateName(resultSet.getString(7));
				ticket.setState(state);
				User assignedUser = new User();
				assignedUser.setFirstName(resultSet.getString(8));
				assignedUser.setLastName(resultSet.getString(9));
				assignedUser.setUserId(resultSet.getInt(10));
				ticket.setAssignedToUser(assignedUser);
				TicketComment lastTicketComment = new TicketComment();
				lastTicketComment.setDateTime(resultSet.getTimestamp(11));
				ticket.getComments().add(lastTicketComment);
				tickets.add(ticket);
			}
			setOutParametersAfterExecute();
			totalNumberOfPages.append(totalNumberOfPagesParam.getParameter());

		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}
		return tickets;
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
				User user = new User();
				user.setUserId(resultSet.getInt(4));
				user.setFirstName(resultSet.getString(5));
				user.setLastName(resultSet.getString(6));
				ticketComment.setUser(user);
				ticketComment.setFilePath(resultSet.getString(7));
				ticketComments.add(ticketComment);
			}
		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}
		return ticketComments;
	}

	@Override
	public boolean addCommentToTicket(Ticket ticket) {

		try {

			InOutParam<Integer> ticketCommentIdParam = new InOutParam<Integer>(0, "CommentId", true);
			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
			InOutParam<Timestamp> dateTimeParam = new InOutParam<Timestamp>(
					ticket.getComments().get(ticket.getComments().size() - 1).getDateTime(), "DateTime");
			InOutParam<String> commentParam = new InOutParam<String>(
					ticket.getComments().get(ticket.getComments().size() - 1).getComment(), "Comment");
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(
					ticket.getComments().get(ticket.getComments().size() - 1).getUser().getUserId(), "UserId");
			InOutParam<String> filePathParam = new InOutParam<String>(
					ticket.getComments().get(ticket.getComments().size() - 1).getFilePath(), "FilePath");
			InOutParam<Integer> errorParam = new InOutParam<Integer>(0, "Error", true);
			prepareExecution(StoredProceduresNames.AddCommentToTicket, ticketCommentIdParam, ticketIdParam,
					dateTimeParam, commentParam, userIdParam, filePathParam, errorParam);
			execute();
			if (errorParam.getParameter() == 0) {

				ticket.getComments().get(ticket.getComments().size() - 1)
						.setCommentId(ticketCommentIdParam.getParameter());
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

	@Override
	public boolean assignTicket(Ticket ticket) {

		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(ticket.getAssignedToUser().getUserId(), "UserId");
			prepareExecution(StoredProceduresNames.AssignTicketToAdmin, ticketIdParam, userIdParam);
			execute();
		} catch (SQLException e) {

			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}

	@Override
	public boolean closeTicket(Ticket ticket) {

		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
			prepareExecution(StoredProceduresNames.CloseTicket, ticketIdParam);
			execute();
		} catch (SQLException e) {

			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}

	@Override
	public ArrayList<User> getAdminsForCategory(Category category) {

		ArrayList<User> admins = new ArrayList<User>();
		try {

			InOutParam<Integer> categoryIdParam = new InOutParam<Integer>(category.getCategoryId(), "CategoryId");
			prepareExecution(StoredProceduresNames.GetAdminsForCategory, categoryIdParam);
			ResultSet resultSet = execute();
			while (resultSet.next()) {

				User admin = new User();
				admin.setUserId(resultSet.getInt(1));
				admin.setFirstName(resultSet.getString(2));
				admin.setLastName(resultSet.getString(3));
				admins.add(admin);
			}
		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}
		return admins;
	}

	@Override
	public boolean changeTicketPriority(Ticket ticket) {

		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
			InOutParam<Integer> priorityIdParam = new InOutParam<Integer>(ticket.getPriority().getPriorityId(),
					"PriorityId");
			InOutParam<Integer> errorParam = new InOutParam<Integer>(0, "Error");
			prepareExecution(StoredProceduresNames.ChangeTicketPriority, ticketIdParam, priorityIdParam, errorParam);
			execute();
			if (errorParam.getParameter() == 1) {

				return false;
			}
		} catch (SQLException e) {

			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}

	@Override
	public ArrayList<Priority> getPriorities() {

		ArrayList<Priority> priorities = new ArrayList<Priority>();
		try {

			prepareExecution(StoredProceduresNames.GetPriorities);
			ResultSet resultSet = execute();
			while (resultSet.next()) {

				Priority priority = new Priority();
				priority.setPriorityId(resultSet.getInt(1));
				priority.setPriorityName(resultSet.getString(2));
				priorities.add(priority);
			}
		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}
		return priorities;
	}
}
