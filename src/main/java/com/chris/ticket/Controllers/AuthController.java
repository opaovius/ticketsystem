package com.chris.ticket.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chris.ticket.Config.JwtConfig;
import com.chris.ticket.Dtos.LoginDtos.JwtResponse;
import com.chris.ticket.Dtos.LoginDtos.LoginRequest;
import com.chris.ticket.Repositories.UserRepository;
import com.chris.ticket.Services.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final JwtConfig jwtConfig;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(
			@Valid @RequestBody LoginRequest request,
			HttpServletResponse response){
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
						)
				);
		
		var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
		var accessToken = jwtService.generateAccessToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		
		var cookie = new Cookie("refreshToken", refreshToken.toString());
		cookie.setHttpOnly(true);
		cookie.setPath("/auth/refresh");
		cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
		cookie.setSecure(false);
		response.addCookie(cookie);
		
		
		return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken) {
		
		var jwt = jwtService.parseToken(refreshToken);
		
		if(jwt == null || jwt.isExpired()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		var userId = jwt.getIdFromToken();
		var user = userRepository.findById(userId).orElseThrow();
		var accessToken = jwtService.generateAccessToken(user);
		
		return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {

	    var cookie = new Cookie("refreshToken", null);
	    cookie.setHttpOnly(true);
	    cookie.setPath("/auth/refresh");
	    cookie.setMaxAge(0);
	    cookie.setSecure(false);
	    response.addCookie(cookie);

	    return ResponseEntity.ok().build();
	}
	
	
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Void> handleBadCredentialsException() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
