package com.dxc.soporte.service;

import java.io.IOException;
import java.util.List;

import com.dxc.soporte.model.TicketModel;

public interface ElasticService {
	
	public abstract List<TicketModel>  listRecomendedTickets(String titulo) throws IOException; 
	//tengo el servicio ElasticService con el metodo ListRecomendedTickets 
	//con un argumento string de titulo, sobre el que montaré la query de Elastic
	
	//esto me devolverá un List de TIcketModel de los que extraeré unicamente titulo y resolucion

}
