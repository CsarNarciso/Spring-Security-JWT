package com.cesar.JWT.security.jwt;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {

	
	public static String generateToken(String authUsername) throws JWTCreationException {
		
		return JWT.create()
				.withIssuer( "Csar" ) //Token provider (company, server)
				.withIssuedAt( Instant.now() ) 
				.withExpiresAt( Instant.now().plusMillis( timeExpiration ))
				.withClaim( "username", authUsername )
				.sign( Algorithm.HMAC256( secret.getBytes() ));
	}
	
	
	
	public static String verifyTokenAndGetUsername(String bearerToken) {
		
		if ( bearerToken != null && bearerToken.startsWith("Bearer ") ) {
			
			try {	
				
				String token = bearerToken.substring(7);
				
				JWTVerifier verifier = JWT.require( Algorithm.HMAC256( secret.getBytes() ))
						.withIssuer( "Csar" )
						.build();
				
				DecodedJWT jwtDecoded = verifier.verify( token );
				
				return jwtDecoded.getClaim( "username" ).asString();	
			}
			catch (Exception e) {}
		}
		
		return null;
	}
	
	//This must be in an external file to more security
	private static String secret = "cG9ueWJyYW5jaGNyZWF0dXJldG9ybnBsZW50eWN1cnJlbnRtZW1vcnloaW1zZWxmZGU=";
	private static int timeExpiration = 9999999;
}
