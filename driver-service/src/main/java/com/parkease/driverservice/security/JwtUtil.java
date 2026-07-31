package com.parkease.driverservice.security;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

	private final SecretKey secretKey = Jwts.SIG.HS256.key().build();
	private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    public String generateToken(String email) {
    	return Jwts.builder()
    			.subject(email)
    			.issuedAt(new Date())
    			.expiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
    			.signWith(secretKey)
    			.compact();
    }
    
    public String extractEmail(String token) {
    	return Jwts.parser()
    			.verifyWith(secretKey)
    			.build()
    			.parseSignedClaims(token)
    			.getPayload()
    			.getSubject();
    }
    
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}