package com.dxc.soporte.controlador;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.dxc.soporte.constatntes.ViewConstant;

@Controller
public class LoginController {
	
	private static final Log LOGGER = LogFactory.getLog(LoginController.class);
	

	/*
	 * @GetMapping("/") public String redirectToLogin() {
	 * LOGGER.info("Metodo: redirectToLogin()"); return "redirect:/login"; }
	 */
	
	/*
	 * @GetMapping("/login")//la vista de login puede mostrarse recogiendo un
	 * parametro entregado (un error devuelto de un login fallido x ej), por tanto
	 * el método debe poder recoger una variable public String showLoginForm(Model
	 * model, @RequestParam(name="error", required=false) String error,
	 * 
	 * @RequestParam(name="logout", required=false) String logout) {
	 * LOGGER.info("Metodo: showLoginForm() -- parametros: error=" + error +
	 * ", logout" + logout); model.addAttribute("error", error); //model add
	 * atributo de nombre error y contenido un string error
	 * model.addAttribute("logout", logout); model.addAttribute("UserCredentials",
	 * new UserCredential());//la vista está gestionando un objeto de tipo
	 * userCredential con el nomber th:object=${UserCredentials //return "login";
	 * //devuelve una vista login return ViewConstant.LOGIN; }
	 */
	
	/*
	 * @PostMapping("/logincheck") //cuando hagamos click en login el model recibe
	 * un objeto de tipo UserCredential llamado credencialdeUsuario public String
	 * loginCheck(@ModelAttribute(name="UserCredentials") UserCredential
	 * credencialesUsuario) { LOGGER.info("Metodo: loginCheck() -- parametros: "+
	 * credencialesUsuario.toString()); //aqui un check de seguridad que valida el
	 * user y pass de credencialesUsuario
	 * if(credencialesUsuario.getUsername().equals("user") &&
	 * credencialesUsuario.getPassword().equals("user")) { //return "contacts";
	 * //aqui devolvere en su momento la vista de incidencias paginada para cada
	 * usuario return "redirect:/contacts/showcontacts"; } //si no se cumple,
	 * devolvemos a la vista de login con el parámetro error return
	 * "redirect:/login?error";
	 * 
	 * }
	 */
	
	@GetMapping("/login")//la vista de login puede mostrarse recogiendo un parametro entregado (un error devuelto de un login fallido x ej), por tanto el método debe poder recoger una variable 
	public String showLoginForm(Model model, @RequestParam(name="error", required=false) String error, 
											 @RequestParam(name="logout", required=false) String logout) {				
		LOGGER.info("Metodo: showLoginForm() -- parametros: error=" + error + ", logout" + logout);
		model.addAttribute("error", error);
		//model add atributo de nombre error y contenido un string error
		model.addAttribute("logout", logout);
		//return "login";	//devuelve una vista login	
		return ViewConstant.LOGIN;
	}
	
	@GetMapping({"/loginsuccess","/"}) //cuando hagamos click en login el model recibe un objeto de tipo UserCredential llamado credencialdeUsuario
	public String loginCheck() {
		//aqui un check de seguridad que valida el user y pass de credencialesUsuario
			//return "contacts"; //aqui devolvere en su momento la vista de incidencias paginada para cada usuario
			
		
			//return "redirect:/contacts/showcontacts";
			return "redirect:/tickets/showtickets";
	}
	
}
