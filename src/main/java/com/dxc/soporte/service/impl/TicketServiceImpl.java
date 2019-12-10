package com.dxc.soporte.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dxc.soporte.component.TicketConverter;
//import com.dxc.soporte.controlador.TicketController;
import com.dxc.soporte.entity.Ticket;
import com.dxc.soporte.model.TicketModel;
import com.dxc.soporte.repository.TicketRepository;
import com.dxc.soporte.service.TicketService;

@Service("ticketServiceImpl") // generamos un servicio
public class TicketServiceImpl implements TicketService {
//aqui implementamos los métodos anunciados en la interface
	// si añadimos un método en la interface, veremos como aqui nos solicita que lo
	// implementemos

	private static final Log LOGGER = LogFactory.getLog(TicketServiceImpl.class);

	@Autowired
	@Qualifier("ticketRepository")
	private TicketRepository ticketRepository;

	@Autowired
	@Qualifier("ticketConverter")
	private TicketConverter ticketConverter;

	@Override
	public TicketModel addTicket(TicketModel TicketModel) {

		// nos vamos al TicketController para inyectar el servicio
		// y aqui inyectamos el repositorio para añadir el usuario
		Ticket ticket = ticketRepository.save(ticketConverter.convertTicketModel2Ticket(TicketModel));
		return ticketConverter.convertTicket2TicketModel(ticket);
	}
	
	@Override
	public TicketModel updateTicket(TicketModel TicketModel) {

		// nos vamos al TicketController para inyectar el servicio
		// y aqui inyectamos el repositorio para añadir el usuario
		Ticket ticket = ticketRepository.saveAndFlush(ticketConverter.convertTicketModel2Ticket(TicketModel));
		//Ticket ticket = ticketRepository.save(ticketConverter.convertTicketModel2Ticket(TicketModel));
		return ticketConverter.convertTicket2TicketModel(ticket);
	}

	@Override
	public List<TicketModel> listAllTickets() {
		List<Ticket> tickets = ticketRepository.findAll();// esto devuelve un list de entidades contact que debemos
															// convertir en contactmodel

		List<TicketModel> ticketsModel = new ArrayList<TicketModel>();
		for (Ticket ticket : tickets) {
			ticketsModel.add(ticketConverter.convertTicket2TicketModel(ticket));
		}
		Collections.reverse(ticketsModel);// da la vuelta a la list
		return ticketsModel;
	}

	@Override
	public Ticket findTicketById(int id) {
		return ticketRepository.findById(id);

	}

	@Override
	public TicketModel findTicketModelById(int id) {
		// TODO Auto-generated method stub
		return ticketConverter.convertTicket2TicketModel(findTicketById(id));
	}

	@Override
	public void removeTicket(int id) {
		Ticket ticket = findTicketById(id);
		if (ticket != null) {
			ticketRepository.delete(ticket);
		} else {

		}

	}

	@Override
	public List<TicketModel> listAllTicketsByDesignated(String designated) {

		List<Ticket> tickets = ticketRepository.findAllByDesignated(designated);// esto devuelve un list de entidades
																				// ticket que debemos
		// convertir en contactmodel
		LOGGER.info("tickets recuperados: " + tickets.size());

		List<TicketModel> ticketsModel = new ArrayList<TicketModel>();
		for (Ticket ticket : tickets) {
			ticketsModel.add(ticketConverter.convertTicket2TicketModel(ticket));
		}
		Collections.reverse(ticketsModel);
		return ticketsModel;
	}

	@Override
	public List<String> listAllUsers() {
			List<Ticket> tickets = ticketRepository.findAll();// esto devuelve un list de entidades contact que debemos
																// convertir en contactmodel

			List<String> sopostistas = new ArrayList<String>();
			for (Ticket ticket : tickets) {
				sopostistas.add(ticket.getDesignated());
			}
			//eliminamos duplicados
			Set<String> hs = new HashSet<>();
			hs.addAll(sopostistas);
			sopostistas.clear();
			sopostistas.addAll(hs);
			//
			return sopostistas;
	}
}
