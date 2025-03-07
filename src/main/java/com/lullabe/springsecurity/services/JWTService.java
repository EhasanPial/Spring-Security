package com.lullabe.springsecurity.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

	String extractUserName(String token);

	String generateToken(UserDetails userDetails);
	
	String isValidToken(UserDetails userDetails);

}
