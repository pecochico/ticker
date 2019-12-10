package com.dxc.soporte.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.dxc.soporte.entity.Ticket;

@Repository("ticketRepository")
public interface TicketRepository  extends JpaRepository<Ticket, Serializable> {
	
	public abstract Ticket findById(int Id);
	
	public abstract Ticket findByDesignated(String designated);

	public abstract List<Ticket> findAllByDesignated(String designated);
	
	//public abstract List<String>  listAllUsers();
	
	//public abstract Ticket findTicketById(int id);
	
}
