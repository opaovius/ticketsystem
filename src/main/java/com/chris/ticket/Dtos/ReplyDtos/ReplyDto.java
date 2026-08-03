package com.chris.ticket.Dtos.ReplyDtos;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDto {

	private long id;

	private String text;

	private String author;

	private Date createdAt;

}
