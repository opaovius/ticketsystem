package com.chris.ticket.Mappers;

import org.mapstruct.Mapper;

import com.chris.ticket.Dtos.ReplyDtos.CreateReplyRequest;
import com.chris.ticket.Dtos.ReplyDtos.ReplyDto;
import com.chris.ticket.Entities.Reply;

@Mapper(componentModel = "spring")
public interface ReplyMapper {

	public Reply toEntity(CreateReplyRequest request);
	
	public ReplyDto toDto(Reply reply);
}
