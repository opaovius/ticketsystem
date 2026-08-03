package com.chris.ticket.Services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chris.ticket.Entities.User;
import com.chris.ticket.Repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SecurityService {
	
	private final UserRepository userRep; 

	public User getCurrentUser() {

	    String userId = SecurityContextHolder
	            .getContext()
	            .getAuthentication()
	            .getName();

	    return userRep.findById(Long.valueOf(userId))
	            .orElseThrow(() -> new RuntimeException("User not found"));
	}
	
}
