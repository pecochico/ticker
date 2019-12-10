package com.dxc.soporte.controlador;

import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dxc.soporte.model.TicketModel;
import com.dxc.soporte.service.ElasticService;
import com.dxc.soporte.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
	
	private static final Log LOGGER = LogFactory.getLog(TicketController.class);
	
	String titulo_busqueda = new String();
	
	@Autowired
	@Qualifier("ticketServiceImpl") 
	private TicketService ticketService;
	
	@Autowired
	@Qualifier("elasticServiceImpl") 
	private ElasticService elasticService;
	
	
	@GetMapping("/cancel")
	public String cancel() {
		//return ViewConstant.CONTACTS;
		return "redirect:/tickets/showtickets";
	}
	
	//crear un ticket nuevo
	//@PreAuthorize("hasRole('ROLE_N2') or hasRole('ROLE_N1')")
	@PreAuthorize("hasRole('ROLE_N1')")
	@GetMapping("/ticketform")
	public String redirectTicketForm (Model model) throws IOException {//el formulario gestiona un modelo ContactModel, (clase con todos los atributos de un nuevo contacto), por  tanto hay que añadirselo
		TicketModel ticket = new TicketModel();
		model.addAttribute("ticketModel", ticket);
		//model.addAttribute("recomendaciones", elasticService.listRecomendedTickets(ticket.getTitle()));
		//return ViewConstant.TICKET_FORM;
		return "ticketform.html";
	}
	
	//leer un ticket 
	@PreAuthorize("hasRole('ROLE_N2') or hasRole('ROLE_N1')")
	@GetMapping("/ticketinfo")
	public String redirectTicketInfo(@RequestParam(name="id", required=false) int id, @RequestParam(name="usa_recomendacion", required=false) int usa_recomendacion, @RequestParam(name="recomendacion", required=false) String recomendacion, Model model) throws IOException {//el formulario gestiona un modelo ContactModel, (clase con todos los atributos de un nuevo contacto), por  tanto hay que añadirselo
		TicketModel ticket = new TicketModel();
		if(id !=0) {
			ticket = ticketService.findTicketModelById(id);			
		}
		if(recomendacion !=null) {
			ticket.setResolution(recomendacion);	
			ticket.setUsa_recomendacion(usa_recomendacion);	
		}
		model.addAttribute("ticketModel", ticket);
		model.addAttribute("recomendaciones", elasticService.listRecomendedTickets(ticket.getTitle()));
		model.addAttribute("recomendacion", recomendacion);
		model.addAttribute("usa_recomendacion", usa_recomendacion);
		//return ViewConstant.TICKET_INFO;
		//---------------------------------------------------
		return "ticketinfo.html";
	}
	
	/*
	 * @ModelAttribute("recomendaciones") public List<TicketModel> recomendaciones()
	 * throws IOException { return
	 * elasticService.listRecomendedTickets(titulo_busqueda); }
	 */
	
	@PostMapping("/addticket")
	public String addTicket(@ModelAttribute(name="ticketModel")TicketModel ticketModel, Model model) {//el @modelatribute name se corresponde con el thobject que maneja el post de la vista. el Model se usa para añadir un comentario en la vista
		LOGGER.info("Metodo: addTIcket() -- parametros: "+ ticketModel.toString());
		// utilizamos el servicio para añadir el contacto, con el servicio
		Timestamp fecha_open = new Timestamp(System.currentTimeMillis());
		ticketModel.setFecha_open(fecha_open);
		if(ticketModel.getState()== "") {
			String abierto = "abierto";
			ticketModel.setState(abierto);
		}
		if(null != ticketService.addTicket(ticketModel)) {
			model.addAttribute("added", 1);//esto disparará el if en la vista, visualizando el add contact ok al tener result valor = 1
			LOGGER.info("result 1 añadido");
		}else{
			model.addAttribute("added", 0);//esto disparará el if en la vista, visualizando el add contact ok al tener result valor = 0
			LOGGER.info("result 0 error");
		}; //le pasamos el contactModel que viene del formulario post
		model.addAttribute("added", 1);
		//return ViewConstant.TICKETS;
		return "redirect:/tickets/showtickets";
	}
	
	@PostMapping("/updateticket")
	public String updateTicket(@ModelAttribute(name="ticketModel")TicketModel ticketModel, Model model) {//el @modelatribute name se corresponde con el thobject que maneja el post de la vista. el Model se usa para añadir un comentario en la vista
		LOGGER.info("Metodo: updateTIcket() -- parametros: "+ ticketModel.toString());
		//LOGGER.info("ffuera de if"+ticketModel.getState());
		if(ticketModel.getState().equals("cerrado")) {
			//LOGGER.info("dentro de if");
			Timestamp fecha_open = ticketModel.getFecha_open();
			Timestamp fecha_close = new Timestamp(System.currentTimeMillis());
			ticketModel.setFecha_close(fecha_close);
			//LOGGER.info("fecha_close " +fecha_close);
			//LOGGER.info("fecha_open " +fecha_open);
			long milliseconds = fecha_close.getTime() - fecha_open.getTime();
			int tiempo = (int) milliseconds / 60000; //pasamos milisegundos a minutos
			ticketModel.setTiempo_medio(tiempo);
			//LOGGER.info("tiempo de cierre" +tiempo);
			
		}
		// utilizamos el servicio para añadir el contacto, con el servicio
		
		if(null != ticketService.updateTicket(ticketModel)) {
			model.addAttribute("updated", 1);//esto disparará el if en la vista, visualizando el add contact ok al tener result valor = 1
			LOGGER.info("result 1 updated");
		}else{
			model.addAttribute("updated", 0);//esto disparará el if en la vista, visualizando el add contact ok al tener result valor = 0
			LOGGER.info("updated 0 error");
		}; //le pasamos el contactModel que viene del formulario post
		
		
		//return ViewConstant.CONTACTS;
		return "redirect:/tickets/showtickets";
	}
	
	
	
	@ModelAttribute("listasoportistas")//devuelve lista de tecnicos de soporte para el desplegable a utilizar a la hora de asignar un ticket
	   public List<String> listaSoportistas() {
		List<String> lista = ticketService.listAllUsers();//
	      return lista;
	   }
	
	
	@GetMapping("/showtickets")
	public ModelAndView showTickets() {
		//ModelAndView mav = new ModelAndView(ViewConstant.TICKETS);//al modelo le pasamos la vista de contactos
		ModelAndView mav = new ModelAndView("tickets.html");//al modelo le pasamos la vista de contactos
		
		//-----------------
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  String username = ((UserDetails)principal).getUsername();
		  mav.addObject("username", username);
		} else {
		  //String username = principal.toString();
		  //mav.addObject("username", username);
		}
		
		
		//---------------
		
		
		//User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//principal es el usuario ahora logeado
		//User pringao =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//System.out.println("User has authorities: " + pringao);
		//mav.addObject("username", user.getUsername());
		
		//mav.addObject("tickets", ticketService.listAllTickets()); //añadimos al mav el objeto Contacts que llama al servicio para listar todos los contactos 
		LOGGER.info("usuario autenticado "+ ((UserDetails)principal).getUsername());
		
		//lista.contains("StringToBeChecked");
		List<String> lista = ticketService.listAllUsers();
		if(lista.contains(((UserDetails)principal).getUsername())) {		
			mav.addObject("tickets", ticketService.listAllTicketsByDesignated(((UserDetails)principal).getUsername()));
		}else {
			mav.addObject("tickets", ticketService.listAllTickets());
			mav.addObject("admin",true);//utilizado para visualizar el icono de eliminacion de ticket
		
		}

		return mav;

		
	}
	
	@GetMapping("/removeticket")
	public ModelAndView removeTIcket(@RequestParam(name="id", required=true) int id) {
		ticketService.removeTicket(id);
		return showTickets();
		
	}
	
}
