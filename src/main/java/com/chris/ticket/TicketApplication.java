package com.chris.ticket;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.chris.ticket.Entities.Role;
import com.chris.ticket.Entities.Ticket;
import com.chris.ticket.Entities.User;
import com.chris.ticket.Services.TicketService;
import com.chris.ticket.Services.UserService;

@SpringBootApplication
public class TicketApplication {

	public static void main(String[] args) {
		
		ApplicationContext cont =  SpringApplication.run(TicketApplication.class, args);
		
		
		var user = new User(null, "John", "1234", "John@mail.com", Role.CUSTOMER);
		
		var ticket = new Ticket(null, "text", "header", 0, null, null, user);
		
		UserService userSer = cont.getBean(UserService.class); 
		TicketService ticketSer = cont.getBean(TicketService.class);
		
		//userSer.registerUser(user);
		
		
		//IO.println(user.toString());
		
		//userSer.removeUserById(1L);
		
		IO.println(ticket.getUser());
		
		//ticketSer.registerTicket(ticket);
		
	}

}