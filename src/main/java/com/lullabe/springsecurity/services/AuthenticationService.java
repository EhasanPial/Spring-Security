package com.lullabe.springsecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lullabe.springsecurity.entity.User;
import com.lullabe.springsecurity.repository.AuthencationResponse;
import com.lullabe.springsecurity.repository.UserRepository;
import com.lullabe.springsecurity.services.impl.JWTServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository repository;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final JWTServiceImpl jwtService;

	@Autowired
	private final AuthenticationManager authenticationManager;

	public AuthencationResponse register(User request) {
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setSecondName(request.getSecondName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user.setRole(request.getRole());

		user = repository.save(user);

		String token = jwtService.generateToken(user);

		return new AuthencationResponse(token);

	}

	public AuthencationResponse authenticate(User request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
						)) ;
		
		User user = repository.findByEmail(request.getUsername()).orElseThrow();
		String token = jwtService.generateToken(user);
		
		return new AuthencationResponse(token) ;
		
		
		
	}

}
