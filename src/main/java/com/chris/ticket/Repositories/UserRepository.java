package com.chris.ticket.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chris.ticket.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String string);

	Optional<User> findByEmail(String email);

	@Query("""
			     SELECT u FROM User u
			WHERE u.role = 'SUPPORT'
			ORDER BY (
			    SELECT COUNT(t) FROM Ticket t
			    WHERE t.supporter = u AND t.status <> 'SOLVED'
			) ASC
			     """)
	List<User> findSupportUsersOrderedByOpenTicketCount();

}
