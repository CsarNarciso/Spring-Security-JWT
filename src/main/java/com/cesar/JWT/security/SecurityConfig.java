package com.cesar.JWT.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http
				
				.sessionManagement( session -> session
						
						.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) 
				)
				.authorizeHttpRequests( request -> request
						
						.requestMatchers("/login").permitAll()
						.anyRequest().authenticated() 
				)
				
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
