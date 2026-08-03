package com.chris.ticket.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chris.ticket.Dtos.ReplyDtos.CreateReplyRequest;
import com.chris.ticket.Dtos.ReplyDtos.ReplyDto;
import com.chris.ticket.Exceptions.TicketNotFoundException;
import com.chris.ticket.Mappers.ReplyMapper;
import com.chris.ticket.Repositories.ReplyRepository;
import com.chris.ticket.Repositories.TicketRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReplyService {

	private final ReplyRepository replyRep;
	private final SecurityService securitySer;
	private final ReplyMapper replyMap;
	private final TicketRepository ticketRep;
	
	public void removeReply(Long id) {
		replyRep.deleteById(id);
	}
	
	public ReplyDto createReply(CreateReplyRequest request) {
		
		var reply = replyMap.toEntity(request);
		var user = securitySer.getCurrentUser();
		var ticket = ticketRep.findById(request.getId()).orElseThrow(() -> new TicketNotFoundException("Ticket mit ID " + request.getId() + " nicht gefunden"));
		
		reply.setAuthor(user.getName());
		reply.setTicket(ticket);
		
		var savedReply = replyRep.save(reply);
		
		return replyMap.toDto(savedReply);
	}
	
	public List<ReplyDto> getAllReplies(Long ticketId){
		
		return replyRep.findAllByTicket_Id(ticketId)
	            .stream()
	            .map(replyMap::toDto)
	            .toList();
	}
}
