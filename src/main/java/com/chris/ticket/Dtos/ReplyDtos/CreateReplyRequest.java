package com.chris.ticket.Dtos.ReplyDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateReplyRequest {

	@NotBlank(message = "Text is required")
	String text;
	
	Long id;
}
