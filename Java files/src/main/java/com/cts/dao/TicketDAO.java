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
			InOutParam<Integer> categoryIdParam = new InOutParam<Integer>(ticket.getCategory().getCategoryId(),
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
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.CreateTicket, subjectParam, categoryIdParam, timestampParam,
					commentParam, userIdParam, filePathParam, priorityIdParam, ticketIdParam, commentIdParam,
					errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 0) {

				if (ticketIdParam.getParameter() != null) {

					ticket.setTicketId(ticketIdParam.getParameter());
					ticket.getComments().get(0).setTicketId(ticketIdParam.getParameter());
				}
				if (commentIdParam.getParameter() != null) {

					ticket.getComments().get(0).setCommentId(commentIdParam.getParameter());
				}
				if (userIdParam.getParameter() != null) {

					ticket.getComments().get(0).getUser().setUserId(userIdParam.getParameter());
				}
			} else {

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
	public ArrayList<Ticket> getTickets(ViewTicketsRequest viewTicketsRequest, StringBuilder totalNumberOfPages) {

		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		try {

			InOutParam<Integer> userIdParam = new InOutParam<Integer>(viewTicketsRequest.getUser().getUserId(),
					"UserId");
			InOutParam<Integer> requestedPageNumberParam = new InOutParam<Integer>(
					viewTicketsRequest.getRequestedPageNumber(), "RequestedPageNumber");
			InOutParam<Integer> ticketsPerPageParam = new InOutParam<Integer>(viewTicketsRequest.getTicketsPerPage(),
					"TicketsPerPage");
			InOutParam<String> textToSearchParam = new InOutParam<String>(viewTicketsRequest.getTextToSearch(),
					"TextToSearch");
			InOutParam<String> searchTypeParam = new InOutParam<String>(viewTicketsRequest.getSearchType(),
					"SearchType");
			InOutParam<Integer> selectedCategoryIdParam = new InOutParam<Integer>(
					viewTicketsRequest.getSelectedCategory().getCategoryId(), "SelectedCategoryID");
			InOutParam<Integer> selectedPriorityIdParam = new InOutParam<Integer>(
					viewTicketsRequest.getSelectedPriority().getPriorityId(), "SelectedPriorityID");
			InOutParam<Integer> selectedStateIdParam = new InOutParam<Integer>(
					viewTicketsRequest.getSelectedState().getStateId(), "SelectedStatusID");
			InOutParam<String> sortTypeParam = new InOutParam<String>(viewTicketsRequest.getSortType(), "SortType");
			InOutParam<Boolean> isSearchASCParam = new InOutParam<Boolean>(viewTicketsRequest.getIsSearchASC(),
					"IsSearchASC");
			InOutParam<Integer> totalNumberOfPagesParam = new InOutParam<Integer>(0, "TotalNumberOfPages", true);

			if (viewTicketsRequest.getTypeOfRequest() == 0) {

				prepareExecution(StoredProceduresNames.GetPersonalTickets, userIdParam, requestedPageNumberParam,
						ticketsPerPageParam, textToSearchParam, searchTypeParam, selectedCategoryIdParam,
						selectedPriorityIdParam, selectedStateIdParam, sortTypeParam, isSearchASCParam,
						totalNumberOfPagesParam);
			} else {

				prepareExecution(StoredProceduresNames.GetManageTickets, userIdParam, requestedPageNumberParam,
						ticketsPerPageParam, textToSearchParam, searchTypeParam, selectedCategoryIdParam,
						selectedPriorityIdParam, selectedStateIdParam, sortTypeParam, isSearchASCParam,
						totalNumberOfPagesParam);

			}
			ResultSet resultSet = execute(true);

			while (resultSet.next()) {

				Ticket ticket = new Ticket();
				ticket.setTicketId(resultSet.getInt("TicketId"));
				ticket.setSubject(resultSet.getString("Subject"));
				TicketComment firstTicketComment = new TicketComment();
				firstTicketComment.setDateTime(resultSet.getTimestamp("SubmitDate"));
				ticket.getComments().add(firstTicketComment);
				Category category = new Category();
				category.setCategoryName(resultSet.getString("CategoryName"));
				ticket.setCategory(category);
				State state = new State();
				state.setStateName(resultSet.getString("StateName"));
				ticket.setState(state);
				User assignedUser = new User();
				assignedUser.setFirstName(resultSet.getString("AssignedUserName"));
				ticket.setAssignedToUser(assignedUser);
				TicketComment lastTicketComment = new TicketComment();
				lastTicketComment.setDateTime(resultSet.getTimestamp("AnswerDate"));
				ticket.getComments().add(lastTicketComment);
				Priority priority = new Priority();
				priority.setPriorityName(resultSet.getString("PriorityName"));
				ticket.setPriority(priority);
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
	public boolean getFullTicket(Ticket ticket) {

		ArrayList<TicketComment> ticketComments = new ArrayList<TicketComment>();
		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
			InOutParam<String> subjectParam = new InOutParam<String>("", "Subject", true);
			InOutParam<Integer> categoryIdParam = new InOutParam<Integer>(0, "CategoryId", true);
			InOutParam<String> categoryNameParam = new InOutParam<String>("", "CategoryName", true);
			InOutParam<Integer> parentCategoryIdParam = new InOutParam<Integer>(0, "ParentCategoryId", true);
			InOutParam<Integer> stateIdParam = new InOutParam<Integer>(0, "StateId", true);
			InOutParam<String> stateNameParam = new InOutParam<String>("", "StateName", true);
			InOutParam<Integer> assignedUserIdParam = new InOutParam<Integer>(0, "AssignedUserId", true);
			InOutParam<String> assignedUserFirstNameParam = new InOutParam<String>("", "AssignedUserFirstName", true);
			InOutParam<String> assignedUserLastNameParam = new InOutParam<String>("", "AssignedUserLastName", true);
			InOutParam<Integer> priorityIdParam = new InOutParam<Integer>(0, "PriorityId", true);
			InOutParam<String> priorityNameParam = new InOutParam<String>("", "PriorityName", true);
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.GetFullTicket, ticketIdParam, subjectParam, categoryIdParam,
					categoryNameParam, parentCategoryIdParam, stateIdParam, stateNameParam, assignedUserIdParam,
					assignedUserFirstNameParam, assignedUserLastNameParam, priorityIdParam, priorityNameParam,
					errCodeParam);
			ResultSet resultSet = execute(true);
			if (resultSet == null) {
				return false;
			}
			while (resultSet.next()) {

				TicketComment ticketComment = new TicketComment();
				ticketComment.setCommentId(resultSet.getInt("CommentId"));
				ticketComment.setTicketId(ticket.getTicketId());
				ticketComment.setDateTime(resultSet.getTimestamp("DateTime"));
				ticketComment.setComment(resultSet.getString("Comment"));
				User user = new User();
				user.setUserId(resultSet.getInt("UserId"));
				user.setFirstName(resultSet.getString("FirstName"));
				user.setLastName(resultSet.getString("LastName"));
				ticketComment.setUser(user);
				ticketComment.setFilePath(resultSet.getString("FilePath"));
				ticketComments.add(ticketComment);
			}
			ticket.setComments(ticketComments);
			setOutParametersAfterExecute();
			if (errCodeParam.getParameter() == 1) {
				return false;
			}
			System.out.println("*arent ID : " + parentCategoryIdParam.getType());
			ticket.setSubject(subjectParam.getParameter());
			ticket.getCategory().setCategoryId(categoryIdParam.getParameter());
			ticket.getCategory().setCategoryName(categoryNameParam.getParameter());
			if (parentCategoryIdParam.getParameter() == null) {
				ticket.getCategory().setParentCategoryId(0);
			} else {
				ticket.getCategory().setParentCategoryId(parentCategoryIdParam.getParameter());
			}

			ticket.getState().setStateId(stateIdParam.getParameter());
			ticket.getState().setStateName(stateNameParam.getParameter());
			if (assignedUserIdParam.getParameter() != null) {

				ticket.getAssignedToUser().setUserId(assignedUserIdParam.getParameter());
			}
			ticket.getAssignedToUser().setFirstName(assignedUserFirstNameParam.getParameter());
			ticket.getAssignedToUser().setLastName(assignedUserLastNameParam.getParameter());
			ticket.getPriority().setPriorityId(priorityIdParam.getParameter());
			ticket.getPriority().setPriorityName(priorityNameParam.getParameter());
		} catch (SQLException e) {

			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
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
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.AddCommentToTicket, ticketCommentIdParam, ticketIdParam,
					dateTimeParam, commentParam, userIdParam, filePathParam, errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 0) {

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
			// ***
			// TODO
			// to be changed from 1 to a real user id from ui
			// ***
			InOutParam<Integer> userWhoDoTheAssignId = new InOutParam<Integer>(1, "UserWhoDoTheAssignId");
			// ***
			prepareExecution(StoredProceduresNames.AssignTicketToAdmin, ticketIdParam, userIdParam,
					userWhoDoTheAssignId);
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
			// ***
			// TODO
			// to be changed from 1 to a real user id from ui
			// ***
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(1, "UserId");
			// ***
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.CloseTicket, ticketIdParam, userIdParam, errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 1) {

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
	public boolean changeTicketPriority(Ticket ticket) {

		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
			InOutParam<Integer> priorityIdParam = new InOutParam<Integer>(ticket.getPriority().getPriorityId(),
					"PriorityId");
			// ***
			// TODO
			// to be changed from 1 to a real user id from ui
			// ***
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(1, "UserId");
			// ***
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.ChangeTicketPriority, ticketIdParam, priorityIdParam, userIdParam,
					errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 1) {

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
	public boolean reopenTicket(Ticket ticket) {

		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
			// ***
			// TODO
			// to be changed from 1 to a real user id from ui
			// ***
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(1, "UserId");
			// ***
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.ReopenTicket, ticketIdParam, userIdParam, errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 1) {

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
	public boolean changeTicketCategory(Ticket ticket) {

		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
			InOutParam<Integer> categoryIdParam = new InOutParam<Integer>(ticket.getCategory().getCategoryId(),
					"CategoryId");
			// ***
			// TODO
			// to be changed from 1 to a real user id from ui
			// ***
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(1, "UserId");
			// ***
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.ChangeTicketCategory, ticketIdParam, categoryIdParam, userIdParam,
					errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 1) {

				return false;
			}
		} catch (SQLException e) {

			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}
}
