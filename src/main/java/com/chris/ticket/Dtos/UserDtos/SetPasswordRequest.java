package com.chris.ticket.Dtos.UserDtos;

import lombok.Data;
import lombok.ToString;

@Data
public class SetPasswordRequest {

	@ToString.Exclude
	private String password;
}
