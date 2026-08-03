package com.chris.ticket.Dtos.UserDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
	
	@NotBlank(message = "Name is required")
	@Size(max = 30, message = "Name must be less than 30 characters")
	private String name;
	
	@NotBlank(message = "Name is required")
	@Email(message = "email must be vaild")
	private String email;
	}
