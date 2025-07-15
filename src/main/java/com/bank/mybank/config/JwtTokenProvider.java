package com.bank.mybank.config;

import java.security.Key;
import java.util.Date;

//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.Authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
//import lombok.Value;
import org.springframework.beans.factory.annotation.Value;


@Component

public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration}")
	private long jwtExpirationDate;
	
	public String generateToken(Authentication authentication) {
		String username =authentication.getName();
		Date currentDate =new Date();
		Date expireDate =new Date(currentDate.getTime()+jwtExpirationDate);
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(key())
				.compact();
		
		
	}
	
	private Key key(){
		byte[] bytes=Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(bytes);
		
	}
	
	public String getUsername(String token) {
		Claims claims =Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJwt(token)
				.getBody();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parse(token);
			return true;
			
		}catch(JwtException e){
			throw new RuntimeException(e);	
		}
	
	}

}
