package com.chris.ticket.Dtos.TicketDtos;

import com.chris.ticket.Entities.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class TicketDto {
	
	private Long id;

	private String text;
	
	private String header;
	
	private Integer priority;
	
	@Enumerated(EnumType.STRING)
	private Status status;

}
