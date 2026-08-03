package com.chris.ticket.Dtos.LoginDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

	@NotBlank(message = "Name is required")
	@Email(message = "email must be vaild")
	private String email;
	
	@NotBlank(message = "Name is required")
	@Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
	private String password;
}
