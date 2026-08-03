package com.chris.ticket.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chris.ticket.Dtos.TicketDtos.CreateTicketRequest;
import com.chris.ticket.Dtos.TicketDtos.GetTicketRequest;
import com.chris.ticket.Dtos.TicketDtos.TicketDto;
import com.chris.ticket.Services.TicketService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {

	private final TicketService ticketSer;
	
	@GetMapping("/customer")
	public ResponseEntity<List<TicketDto>> getAllCustomerTickets(Authentication auth){
		
		var ticketDtos = ticketSer.getAllTicketsOfUser(auth);
		
		return ResponseEntity.ok(ticketDtos);
	}
	
	@GetMapping("/support")
	public ResponseEntity<List<TicketDto>> getAllSupportTickets(Authentication auth){
		
		var ticketDtos = ticketSer.getAllTicketsOfSupport(auth);
		
		return ResponseEntity.ok(ticketDtos);
	}
	
	@PostMapping("/customer/create")
	public ResponseEntity<TicketDto> createTicket(@Valid @RequestBody CreateTicketRequest request){
		
		return  ResponseEntity.ok(ticketSer.createTicket(request));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GetTicketRequest> getTicket(@PathVariable Long id){
		return ResponseEntity.ok(ticketSer.getTicketById(id));
	}
	
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<TicketDto> updateStatus(@PathVariable Long id){
		
		return ResponseEntity.ok(ticketSer.updateStatus(id));
	}
	
	@PutMapping("/close/{id}")
	public ResponseEntity<TicketDto> closeTicket(@PathVariable Long id){
		
		return ResponseEntity.ok(ticketSer.closeTicket(id));
	}
	
}
