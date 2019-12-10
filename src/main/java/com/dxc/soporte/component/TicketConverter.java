package com.dxc.soporte.component;

import org.springframework.stereotype.Component;


import com.dxc.soporte.entity.Ticket;
import com.dxc.soporte.model.TicketModel;

@Component("ticketConverter")
public class TicketConverter {

	public Ticket convertTicketModel2Ticket(TicketModel ticketModel) {
		Ticket ticket = new Ticket();
		ticket.setTitle(ticketModel.getTitle());
		ticket.setResolution(ticketModel.getResolution());
		ticket.setDesignated(ticketModel.getDesignated());
		ticket.setState(ticketModel.getState());
		ticket.setId(ticketModel.getId());		
		ticket.setFecha_open(ticketModel.getFecha_open());
		ticket.setFecha_close(ticketModel.getFecha_close());
		ticket.setTiempo_medio(ticketModel.getTiempo_medio());
		ticket.setUsa_recomendacion(ticketModel.getUsa_recomendacion());
		return ticket;//devuelve una entidad ticket al recibir un modelo ticketModel de argumento
	}
	
	public TicketModel convertTicket2TicketModel(Ticket ticket) {

		TicketModel ticketModel = new TicketModel();
		ticketModel.setTitle(ticket.getTitle());
		ticketModel.setResolution(ticket.getResolution());
		ticketModel.setDesignated(ticket.getDesignated());
		ticketModel.setState(ticket.getState());
		ticketModel.setId(ticket.getId());
		ticketModel.setFecha_open(ticket.getFecha_open());
		ticketModel.setFecha_close(ticket.getFecha_close());
		ticketModel.setTiempo_medio(ticket.getTiempo_medio());
		ticketModel.setUsa_recomendacion(ticket.getUsa_recomendacion());
		return ticketModel;
	}
}
