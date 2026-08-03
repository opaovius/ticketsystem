package com.chris.ticket.Dtos.TicketDtos;

import lombok.Data;

@Data
public class GetTicketRequest {

	private Long id;

	private String text;
	
	private String header;
}
