package com.abdelrahman.spokify.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.abdelrahman.spokify.security.dtos.UserPrinciple;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${TOKEN_SIGNATURE}")
	private String SECRETE_KEY;
		
	public String generateToken(UserPrinciple user) {
		Map<String,Object> claims = new HashMap<>();
		claims.put("userId",user.getId());
		claims.put("userName", user.getUserNameValue());
		claims.put("email", user.getUsername());
		claims.put("profileImage", user.getProfileImage());
		return Jwts.builder()
				.claims()
				.add(claims)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15))
				.subject(user.getUsername())
				.and()
				.signWith(getKey())
				.compact();
	}
	// محتاج اظبط ال key بعدين
	private SecretKey getKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRETE_KEY));
		return key;
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		String email  = extractEmail(token);		
		return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		
		Claims claims = extractAllClaims(token);
		
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parser()
		        .verifyWith(getKey())   // بيتأكد إن التوقيع صحيح بنفس الـsecret
		        .build()
		        .parseSignedClaims(token)
		        .getPayload();

	}
	public String extractEmail(String token) {
		
		return extractClaim(token,Claims::getSubject);
	}
	
	private boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		
		return extractClaim(token,Claims::getExpiration);
	}
}
