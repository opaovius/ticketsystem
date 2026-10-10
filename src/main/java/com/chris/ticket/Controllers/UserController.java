package com.chris.ticket.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chris.ticket.Dtos.UserDtos.ChangePasswordRequest;
import com.chris.ticket.Dtos.UserDtos.GetUsersRequest;
import com.chris.ticket.Dtos.UserDtos.RegisterUserRequest;
import com.chris.ticket.Dtos.UserDtos.RegisterUserResponse;
import com.chris.ticket.Dtos.UserDtos.SetPasswordRequest;
import com.chris.ticket.Dtos.UserDtos.UpdateUserRequest;
import com.chris.ticket.Dtos.UserDtos.UserDto;
import com.chris.ticket.Services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userSer;

	@GetMapping
	public ResponseEntity<List<GetUsersRequest>> getAllUsers(
			@RequestParam(required = false, defaultValue = "", name = "sort") String sortBy) {

		return ResponseEntity.ok(userSer.getAllUsers(sortBy));
	}

	// Abfragen eines bestimmten User mit einer id
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable Long id) {

		return ResponseEntity.ok(userSer.loadUserById(id));
	}

	// Erstellen eines neuen Users
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request,
			UriComponentsBuilder uriBuilder) {

		RegisterUserResponse response = userSer.registerUser(request);

		var uri = uriBuilder.path("/users/{id}").buildAndExpand(response.getUserDto().getId()).toUri();

		return ResponseEntity.created(uri).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {

		return ResponseEntity.ok(userSer.updateUser(id, request));
	}

	// Entfernt einen User aus der Datenbank
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

		userSer.deleteUser(id);

		return ResponseEntity.noContent().build();
	}

	// Aendern des Passwortes eines Users
	@PostMapping("/{id}/change-password")
	public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {

		userSer.changePassword(id, request);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/setPassword")
	public ResponseEntity<UserDto> setPassword(Authentication auth, @RequestBody SetPasswordRequest request) {

		return ResponseEntity.ok(userSer.setPassword(auth, request));
	}
}
