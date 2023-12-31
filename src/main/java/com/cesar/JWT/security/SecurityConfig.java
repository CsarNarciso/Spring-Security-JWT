package com.cesar.JWT.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cesar.JWT.security.filters.JwtAuthenticationFilter;
import com.cesar.JWT.security.filters.JwtAuthorizationFilter;

@Configuration
public class SecurityConfig {

	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
				
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
		jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);		
		
		JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter();
		
		
		return http
				.csrf( csrf -> csrf
						.disable() 
				)
				.sessionManagement( session -> session
						
						.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) 
				)
				.authorizeHttpRequests( request -> request
						
						.requestMatchers(HttpMethod.GET, "/access").authenticated()
				)
				.addFilter( jwtAuthenticationFilter )
				.addFilterBefore( jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class )
				
				.build();
	}
	
	
	
	
	@Bean
	UserDetailsManager userDetailsManager() {
		
		InMemoryUserDetailsManager userDetails = new InMemoryUserDetailsManager();
		
		userDetails.createUser( User.withUsername("a").password( passwordEncoder().encode("1")).build() );
		
		return userDetails;
	}
	
	
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		
		return authConfig.getAuthenticationManager();
	}
	
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();	
	}
	
		
}
