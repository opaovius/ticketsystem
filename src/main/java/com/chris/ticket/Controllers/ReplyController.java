package com.chris.ticket.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chris.ticket.Dtos.ReplyDtos.CreateReplyRequest;
import com.chris.ticket.Dtos.ReplyDtos.ReplyDto;
import com.chris.ticket.Services.ReplyService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
	
	private ReplyService replySer;

	@PostMapping("/create")
	public ResponseEntity<ReplyDto> createReply(@Valid @RequestBody CreateReplyRequest request){
		
		return ResponseEntity.ok(replySer.createReply(request));
	}
	
	//Gibt alle replies aus, fuer ein Ticket, mit dessen id
	@GetMapping("/getAll/{id}")
	public ResponseEntity<List<ReplyDto>> getAllReplies(@PathVariable Long id){
		
		return ResponseEntity.ok(replySer.getAllReplies(id));
	}
	
}
