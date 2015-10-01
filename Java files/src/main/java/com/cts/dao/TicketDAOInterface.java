package com.cts.dao;

import java.util.ArrayList;
import com.cts.entities.Ticket;
import com.cts.entities.ViewTicketsRequest;

public interface TicketDAOInterface {

	public boolean createTicket(Ticket ticket);

	public boolean deleteTicket(Ticket ticket);

	public ArrayList<Ticket> getTickets(ViewTicketsRequest viewTicketsRequest, StringBuilder totalNumberOfPages);

	public boolean getFullTicket(Ticket ticket);

	public boolean addCommentToTicket(Ticket ticket);

	public boolean assignTicket(Ticket ticket);

	public boolean closeTicket(Ticket ticket);

	public boolean changeTicketPriority(Ticket ticket);
	
	public boolean reopenTicket(Ticket ticket);
	
	public boolean changeTicketCategory(Ticket ticket);
}
