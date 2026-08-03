package com.chris.ticket.Services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.chris.ticket.Dtos.TicketDtos.CreateTicketRequest;
import com.chris.ticket.Dtos.TicketDtos.GetTicketRequest;
import com.chris.ticket.Dtos.TicketDtos.TicketDto;
import com.chris.ticket.Entities.Status;
import com.chris.ticket.Exceptions.TicketNotFoundException;
import com.chris.ticket.Mappers.TicketMapper;
import com.chris.ticket.Repositories.TicketRepository;
import com.chris.ticket.Repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketService {

	private final TicketRepository ticketRep;
	private final TicketMapper ticketMap;
	private final SecurityService securitySer;
	private final UserRepository userRep;
	
	public void removeTicketById(Long id) {
		ticketRep.deleteById(id);
	}
	
	public GetTicketRequest getTicketById(Long id) {
		
		var ticket = ticketRep.findById(id).orElseThrow(() -> new TicketNotFoundException("Ticket mit ID " + id + " nicht gefunden"));
		
		return ticketMap.toGetTicketRequest(ticket);
	}
	
	public TicketDto createTicket(CreateTicketRequest request) {
		
		var ticket = ticketMap.toEntity(request);
		var user = securitySer.getCurrentUser();
		//supporter mit den wenigsten tickets zuweisen
		var supporter = userRep.findSupportUsersOrderedByOpenTicketCount().get(0);
		
		ticket.setStatus(Status.OPEN);
		ticket.setUser(user);
		ticket.setSupporter(supporter);
		
		var savedTicket = ticketRep.save(ticket);
		
		return ticketMap.toDto(savedTicket);
	}

	public List<TicketDto> getAllTicketsOfUser(Authentication auth) {

		Long userId = Long.valueOf(auth.getName());

	    return ticketRep.findByUser_Id(userId)
	            .stream()
	            .map(ticketMap::toDto)
	            .toList();
	}
	
	public List<TicketDto> getAllTicketsOfSupport(Authentication auth) {

		Long userId = Long.valueOf(auth.getName());

	    return ticketRep.findBySupporter_Id(userId)
	            .stream()
	            .map(ticketMap::toDto)
	            .toList();
	}
	
	public TicketDto updateStatus(Long id) {
		
		var user = securitySer.getCurrentUser();
		var ticket = ticketRep.findById(id).orElseThrow(() -> new TicketNotFoundException("Ticket mit ID " + id + " nicht gefunden"));
		
		switch(user.getRole()) {
		case CUSTOMER:
			ticket.setStatus(Status.WAITING_FOR_SUPPORT);
			break;
		case SUPPORT:
			ticket.setStatus(Status.WAITING_FOR_CUSTOMER);
			break;
		default:
			ticket.setStatus(Status.OPEN);
		}
		
		var updatedTicket = ticketRep.save(ticket);
		
		return ticketMap.toDto(updatedTicket);
	}
	
	public TicketDto closeTicket(Long id) {
		
		var ticket = ticketRep.findById(id).orElseThrow(() -> new TicketNotFoundException("Ticket mit ID " + id + " nicht gefunden"));
		
		ticket.setStatus(Status.SOLVED);
		
		var savedTicket = ticketRep.save(ticket);
		
		return ticketMap.toDto(savedTicket);
	}
	
}
