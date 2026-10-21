package com.chris.ticket.Dtos.UserDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
public class SetPasswordRequest {

	@ToString.Exclude
	@NotBlank(message = "Password is required")
	@Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
	private String newPassword;
}
