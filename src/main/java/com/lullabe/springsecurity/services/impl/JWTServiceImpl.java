package com.lullabe.springsecurity.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.lullabe.springsecurity.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl {

	private final String SECRET_KEY = "1e47ed8f755733d50bad6be50f69f0cb6c378910af2cdaaff71187e2ae206b04";

	public String generateToken(User user) {
		String token = Jwts.builder().subject(user.getEmail()).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 24 * 60 * 60)).signWith(getSigninKey())
				.compact();
		System.out.println("token is: " + token);
		return token;
	}

	private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	
	public boolean isValid(String token, UserDetails user) {
		String userName = extractUserName(token);
		return userName != null && userName.equals(user.getUsername()) && !isTokenExpired(token); 
	}

	private boolean isTokenExpired(String token) {
		return extractExpiredToken(token).before(new Date());
	}

	private Date extractExpiredToken(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	 private Claims extractAllClaims(String token) {
	        return Jwts
	                .parser()
	                .verifyWith((SecretKey) getSigninKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }

	 
}
