package com.chris.ticket.Dtos.UserDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
public class ChangePasswordRequest {
	
	@NotBlank(message = "Old Password is required")
	@Size(min = 6, max = 30, message = "Old Password must be between 6 and 30 characters")
	@ToString.Exclude
	private String oldPassword;
	
	@NotBlank(message = "New Password is required")
	@Size(min = 6, max = 30, message = "New Password must be between 6 and 30 characters")
	@ToString.Exclude
	private String newPassword;
}
