package com.cesar.JWT.security.filters;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cesar.JWT.security.jwt.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	
	public JwtAuthenticationFilter(JwtUtils jwtUtils) {
	
		this.jwtUtils = jwtUtils;
	}
	
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken authToken = new 
				UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password"));
		
		return getAuthenticationManager().authenticate(authToken);		
	}
	
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
				
		String token = jwtUtils.generateToken( authResult.getName() );
		
		response.addHeader("Authorization", token);
		
		response.setContentType( MediaType.APPLICATION_JSON_VALUE );
		
		response.getWriter().write( token );
	}
	
	
	
	private JwtUtils jwtUtils;
}
