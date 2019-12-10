package com.dxc.soporte.service;

import java.util.List;

import com.dxc.soporte.entity.Ticket;
import com.dxc.soporte.model.TicketModel;

public interface TicketService {

	public abstract TicketModel addTicket(TicketModel ticketModel);
	
	public abstract TicketModel updateTicket(TicketModel ticketModel);
	
	public abstract List<TicketModel>  listAllTickets(); //este método no recibe argumentos, unicamnete devuelve un list de ticketmodels

	public abstract List<TicketModel>  listAllTicketsByDesignated(String designated);
	
	public abstract Ticket findTicketById(int id);
	
	public abstract TicketModel findTicketModelById(int id);
	
	public abstract void removeTicket(int id);
	
	public abstract List<String> listAllUsers();
	
}
