package com.chris.ticket.Services;

import java.util.Date;

import javax.crypto.SecretKey;

import com.chris.ticket.Entities.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Jwt {

	private final Claims claims;
	private final SecretKey secretKey;

	public boolean isExpired() {

		return claims.getExpiration().before(new Date());

	}

	public Long getIdFromToken() {
		return Long.valueOf(claims.getSubject());
	}

	public Role getRoleFromToken() {
		return Role.valueOf(claims.get("role", String.class));
	}
	
	public String toString() {
		return Jwts.builder().claims(claims).signWith(secretKey).compact();
	}

}
