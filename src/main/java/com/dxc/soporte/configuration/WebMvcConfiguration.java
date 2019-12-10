package com.dxc.soporte.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dxc.soporte.component.RequestTimeInterceptor;

@SuppressWarnings("deprecation")
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter{

	@Autowired
	@Qualifier("RequestTimeinterceptador")
	private RequestTimeInterceptor RequestTimeinterceptador;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
registry.addInterceptor(RequestTimeinterceptador);
	}

	
}
