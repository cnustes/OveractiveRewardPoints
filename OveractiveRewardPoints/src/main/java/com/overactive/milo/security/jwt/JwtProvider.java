package com.overactive.milo.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.overactive.milo.security.entity.UserMain;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider 
{
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private int expiration;
	
	public String generateToken(Authentication authentication)
	{
		UserMain userMain = (UserMain)authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(userMain.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	public String getUserNameFromToken(String token)
	{
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody().getSubject();
	}
	
	public boolean validateToken(String token)
	{
		try
		{
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		}catch (MalformedJwtException e) {
			log.error("Malformed Token " + e.getMessage());
		}catch (UnsupportedJwtException e) {
			log.error("Unsupported Token " + e.getMessage());
		}catch (ExpiredJwtException e) {
			log.error("Expired Token " + e.getMessage());
		}catch (IllegalMonitorStateException e) {
			log.error("Empty Token " + e.getMessage());
		}catch (SignatureException e) {
			log.error("Signature fail Token " + e.getMessage());
		}
		
		return false;
	}
}
