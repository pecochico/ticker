package com.dxc.soporte.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component("RequestTimeinterceptador")
public class RequestTimeInterceptor extends HandlerInterceptorAdapter{

private static final Log LOGGER = LogFactory.getLog(RequestTimeInterceptor.class);
	
	
	@Override//se ejecuta antes de entrar en el metodo del controlador
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		request.setAttribute("starttime",System.currentTimeMillis());
		return true;
	}

	@Override//se ejecuta justo antes de devolver la vista en el navegador
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		long starttime = (long) request.getAttribute("starttime");
		LOGGER.info("url solicitada "+request.getRequestURL().toString()+ " totaltime" +(System.currentTimeMillis() - starttime)+"ms");
	}

}
