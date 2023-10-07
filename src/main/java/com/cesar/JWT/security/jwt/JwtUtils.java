package com.cesar.JWT.security.jwt;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {

	
	public String generateToken(Authentication auth) throws JWTCreationException {
		
		return JWT.create()
				.withIssuer( "Csar" ) //Token provider (company, server)
				.withIssuedAt( Instant.now() ) 
				.withExpiresAt( Instant.now().plusMillis( timeExpiration ))
				.withClaim( "username", auth.getName() )
				.sign( algorithm );
	}
	
	
	
	public String verifyTokenAndGetUsername(String token) throws JWTVerificationException {
		
		JWTVerifier verifier = JWT.require( algorithm )
				.withIssuer( "Csar" )
				.build();
		
		DecodedJWT jwtDecoded = verifier.verify( token );
		
		if ( jwtDecoded != null ) {
			
			return jwtDecoded.getClaim( "username" ).asString();
		}
		
		return null;
	}
	
	
	
	
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.timeExpiration}")
	private Long timeExpiration;
	
	private Algorithm algorithm = Algorithm.HMAC256( secret.getBytes() );
}
