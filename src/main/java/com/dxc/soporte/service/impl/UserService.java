package com.dxc.soporte.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dxc.soporte.entity.User;
import com.dxc.soporte.entity.UserRole;
import com.dxc.soporte.repository.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService{//implementa el servicio propio que tiene springSecurity

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;//autowireamos el repositorio
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//el método se encarga de llamar al userRepository
		//findByUsername devuelve una entidad User que debemos transformar en UserDetails
		User user=userRepository.findByUsername(username);
		List<GrantedAuthority> authorities = buildAuthorities(user.getUserRoles());
		return buildUser(user, authorities);
	}
	
	private org.springframework.security.core.userdetails.User buildUser(User user, List<GrantedAuthority> authorities) {
		//return new User(user.getUsername(), user.getPassword(), user.isEnabled(), authorities);
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
		
	}
	
	private List<GrantedAuthority> buildAuthorities(Set<UserRole> userRoles){
		//transforma nuestro set de roles de usuario a un listado de grantedauthorities que es objeto utilizado por spring para almacenar roles
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		//recorremos el set userRoles 
		for(UserRole userRole : userRoles) {//por cada UserRole en el set userRoles
			auths.add(new SimpleGrantedAuthority(userRole.getRole()));}
		return new ArrayList<GrantedAuthority>(auths);
		
	}

}
