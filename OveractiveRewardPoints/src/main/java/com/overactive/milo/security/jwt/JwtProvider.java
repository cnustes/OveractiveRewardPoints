package com.overactive.milo.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.overactive.milo.security.dto.JwtDTO;
import com.overactive.milo.security.entity.UserMain;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import lombok.extern.slf4j.Slf4j;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import java.text.ParseException;

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
		List<String> roles = userMain.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		
		return Jwts.builder()
				.setSubject(userMain.getUsername())
				.claim("roles", roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	public String getUserNameFromToken(String token)
	{
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody().getSubject();
	}
	
	public boolean validateToken(String token)
	{
		try
		{
			Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
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
	
	@SuppressWarnings("unchecked")
	public String refreshToken(JwtDTO jwtDto) throws ParseException
	{
        JWT jwt = JWTParser.parse(jwtDto.getToken());
        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        String nombreUsuario = claims.getSubject();
   
        List<String> roles = (List<String>) claims.getClaim("roles");

        return Jwts.builder()
                .setSubject(nombreUsuario)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
}
