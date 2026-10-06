package com.chris.ticket.Dtos.UserDtos;

import com.chris.ticket.Entities.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {

	@NotBlank(message = "Name is required")
	@Size(max = 30, message = "Name must be less than 30 characters")
	private String name;
	
	@NotBlank(message = "email is required")
	@Email(message = "email must be vaild")
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Role role;
}
