package com.chris.ticket.Dtos.UserDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
	
	@NotBlank(message = "Name is required")
	@Size(min = 6, max = 30, message = "Name must be between 5 and 30 characters")
	private String oldPassword;
	
	@NotBlank(message = "Name is required")
	@Size(min = 6, max = 30, message = "Name must be between 5 and 30 characters")
	private String newPassword;
}
