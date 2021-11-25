package com.overactive.milo.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.overactive.milo.security.service.impl.UserDetailServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilterToken extends OncePerRequestFilter
{
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UserDetailServiceImpl userDetailServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		try {
			String token = getToken(request);
			if(token != null && jwtProvider.validateToken(token))
			{
				String userName = jwtProvider.getUserNameFromToken(token);
				UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(userName);
				
				UsernamePasswordAuthenticationToken auth 
					= new UsernamePasswordAuthenticationToken(userName, userDetails);
				
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}catch (Exception e) {
			log.error("Fail method doFilterInternal " + e.getMessage());
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	private String getToken(HttpServletRequest request)
	{
		String header = request.getHeader("Authorization");
		if(header != null && header.startsWith("Bearer"))
		{
			return header.replace("Bearer", "");
		}
		return null;
	}

}
