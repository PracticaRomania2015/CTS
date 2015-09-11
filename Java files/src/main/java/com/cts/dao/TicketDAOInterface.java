package com.cts.dao;

import java.util.ArrayList;
import com.cts.entities.Category;
import com.cts.entities.Priority;
import com.cts.entities.Ticket;
import com.cts.entities.User;
import com.cts.entities.ViewTicketsRequest;

public interface TicketDAOInterface {

	public boolean createTicket(Ticket ticket);

	public boolean deleteTicket(Ticket ticket);

	public ArrayList<Ticket> getTickets(ViewTicketsRequest viewTicketsRequest, StringBuilder totalNumberOfPages);

	public ArrayList<Category> getCategories(User user);

	public ArrayList<Category> getSubcategories(Category category);

	public boolean getFullTicket(Ticket ticket);

	public boolean addCommentToTicket(Ticket ticket);

	public boolean assignTicket(Ticket ticket);

	public boolean closeTicket(Ticket ticket);

	public ArrayList<User> getAdminsForCategory(Category category);

	public boolean changeTicketPriority(Ticket ticket);

	public ArrayList<Priority> getPriorities();
}
