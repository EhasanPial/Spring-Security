package com.lullabe.springsecurity.repository;

import lombok.Getter;

@Getter
public class AuthencationResponse {
	private String token;
	
	public AuthencationResponse(String token) {
		this.token = token;
	}
	
	
}
