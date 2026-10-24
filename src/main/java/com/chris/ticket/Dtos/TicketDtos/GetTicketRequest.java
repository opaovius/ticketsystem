package com.chris.ticket.Dtos.TicketDtos;

import com.chris.ticket.Entities.Status;

import lombok.Data;

@Data
public class GetTicketRequest {

	private Long id;

	private String text;
	
	private String header;
	
	private Status status;
}
