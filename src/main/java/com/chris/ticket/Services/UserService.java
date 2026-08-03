package com.chris.ticket.Services;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chris.ticket.Repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{

	private final UserRepository userRep;
	
	/*
	public void registerUser(User user) {
		userRep.save(user);
		IO.println("User registered");
	}
	
	public void removeUserById(Long id) {
		userRep.deleteById(id);
		IO.println("User removed");
	}
	*/

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		var user = userRep.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Nutzer konnte nicht gefunden werden"));
		
		return new User(user.getEmail(),
				user.getPassword(),
				Collections.emptyList());
	}
	
}
