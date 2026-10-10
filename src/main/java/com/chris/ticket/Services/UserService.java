package com.chris.ticket.Services;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chris.ticket.Dtos.UserDtos.ChangePasswordRequest;
import com.chris.ticket.Dtos.UserDtos.GetUsersRequest;
import com.chris.ticket.Dtos.UserDtos.RegisterUserRequest;
import com.chris.ticket.Dtos.UserDtos.RegisterUserResponse;
import com.chris.ticket.Dtos.UserDtos.SetPasswordRequest;
import com.chris.ticket.Dtos.UserDtos.UpdateUserRequest;
import com.chris.ticket.Dtos.UserDtos.UserDto;
import com.chris.ticket.Exceptions.ChangePasswordMismatchException;
import com.chris.ticket.Exceptions.EmailAlreadyRegisteredException;
import com.chris.ticket.Mappers.UserMapper;
import com.chris.ticket.Repositories.UserRepository;
import com.chris.ticket.Util.PasswordGen;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRep;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMap;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		var user = userRep.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Nutzer konnte nicht gefunden werden"));

		return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
	}

	public UserDto loadUserById(Long id) {

		var user = userRep.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("Nutzer konnte nicht gefunden werden"));

		return userMap.toDto(user);
	}

	public List<GetUsersRequest> getAllUsers(String sortBy) {

		// Parameter setzen, falls ungueltiger Ausdruck
		if (!Set.of("name", "email", "id", "role").contains(sortBy)) {
			sortBy = "name";
		}

		return  userRep.findAll(Sort.by(sortBy)).stream().map(userMap::toGetUsersRequest).toList();
	}

	public RegisterUserResponse registerUser(RegisterUserRequest request) {
		
		
		if (userRep.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyRegisteredException("Diese Email ist bereits registriert.");
		}

		var user = userMap.toEntity(request);

		// Passwort generieren
		String tempPass = PasswordGen.generate(16);

		// passwort hashen fuer user
		user.setPassword(passwordEncoder.encode(tempPass));
		user.setPasswordSet((byte) 0);

		user = userRep.save(user);

		UserDto userDto = userMap.toDto(user);

		RegisterUserResponse response = new RegisterUserResponse(userDto, tempPass);

		return response;
	}
	
	public UserDto updateUser(Long id, UpdateUserRequest request) {
		
		var user = userRep.findById(id).orElseThrow(() -> new UsernameNotFoundException("Nutzer konnte nicht gefunden werden"));

		userMap.update(request, user);
		userRep.save(user);

		return userMap.toDto(user);
	}
	
	public void deleteUser(Long id) {
		
		var user = userRep.findById(id).orElseThrow(() -> new UsernameNotFoundException("Nutzer konnte nicht gefunden werden"));

		userRep.delete(user);
	}
	
	public void changePassword(Long id, ChangePasswordRequest request) {
		
		var user = userRep.findById(id).orElseThrow(() -> new UsernameNotFoundException("Nutzer konnte nicht gefunden werden"));

		if (!user.getPassword().equals(request.getOldPassword())) {
			throw new ChangePasswordMismatchException("Altes Passwort stimmt nicht!");
		}

		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		userRep.save(user);
	}
	
	public Boolean getPasswordSet(Authentication auth) {
		
		var user = userRep.findById(Long.valueOf(auth.getName()))
				.orElseThrow(() -> new UsernameNotFoundException("Nutzer konnte nicht gefunden werden"));
		
		if(user.getPasswordSet() == (byte) 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public UserDto setPassword(Authentication auth, SetPasswordRequest request) {
		
		IO.println("HIER: " + request.getNewPassword());

		var user = userRep.findById(Long.valueOf(auth.getName()))
				.orElseThrow(() -> new UsernameNotFoundException("Nutzer konnte nicht gefunden werden"));

		user.setPasswordSet((byte) 1);
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));

		user = userRep.save(user);

		return userMap.toDto(user);
	}

}
