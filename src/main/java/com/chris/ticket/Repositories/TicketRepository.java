package com.chris.ticket.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chris.ticket.Entities.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

	List<Ticket> findAllByUser_Id(Long userId);

	List<Ticket> findByUser_Id(Long userId);

	List<Ticket> findBySupporter_Id(Long userId);

}
