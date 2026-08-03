package com.chris.ticket.Dtos.TicketDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTicketRequest {

	@NotBlank(message = "Text is required")
	private String text;
	
	@NotBlank(message = "Header is required")
	private String header;
}
