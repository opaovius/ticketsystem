package com.chris.ticket.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chris.ticket.Entities.Reply;

public interface ReplyRepository extends CrudRepository<Reply, Long>{
	
	public List<Reply> findAllByTicket_Id(Long id); 

}
