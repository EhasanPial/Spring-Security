package com.lullabe.springsecurity.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lullabe.springsecurity.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	public UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepository.findByEmail(username)
				.orElseThrow(
						()-> new UsernameNotFoundException("Username " + username + "not found")
						);

	}

}
