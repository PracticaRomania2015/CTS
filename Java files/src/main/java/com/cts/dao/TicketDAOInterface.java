package com.cts.dao;

import java.util.ArrayList;
import com.cts.entities.Category;
import com.cts.entities.Ticket;
import com.cts.entities.TicketComment;
import com.cts.entities.ViewTicketsRequest;

public interface TicketDAOInterface {

	public boolean createTicket(Ticket ticket);

	public boolean deleteTicket(Ticket ticket);

	public ArrayList<Ticket> getTickets(ViewTicketsRequest viewTicketsRequest, Integer totalNumberOfPages);

	public ArrayList<Category> getCategories();

	public ArrayList<Category> getSubcategories(Category category);

	public ArrayList<TicketComment> getTicketComments(Ticket ticket);

	public boolean addCommentToTicket(Ticket ticket);
}
