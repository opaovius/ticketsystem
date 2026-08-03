package com.chris.ticket.Mappers;

import org.mapstruct.Mapper;

import com.chris.ticket.Dtos.TicketDtos.CreateTicketRequest;
import com.chris.ticket.Dtos.TicketDtos.GetTicketRequest;
import com.chris.ticket.Dtos.TicketDtos.TicketDto;
import com.chris.ticket.Entities.Ticket;

@Mapper(componentModel = "spring")
public interface TicketMapper {
	
	public TicketDto toDto(Ticket ticket);
	
	public Ticket toEntity(CreateTicketRequest request);
	
	public GetTicketRequest toGetTicketRequest(Ticket ticket);

}
