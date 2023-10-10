package com.cesar.JWT.security.filters;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cesar.JWT.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			@SuppressWarnings("unchecked")
			Map<String, Object> userData = new ObjectMapper().readValue(request.getReader(), Map.class);
			
			UsernamePasswordAuthenticationToken authToken = new 
					UsernamePasswordAuthenticationToken(userData.get("username"), userData.get("password"));
		
			return getAuthenticationManager().authenticate(authToken);		
		} 
		catch (IOException e) {
			
			return null;
		}
	}
	
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
				
		String token = JwtUtils.generateToken( authResult.getName() );
				
		response.addHeader("Authorization", token);
				
		response.getWriter().flush();
		
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
}
