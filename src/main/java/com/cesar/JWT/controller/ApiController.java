package com.cesar.JWT.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	
	@PostMapping
	ResponseEntity<?> login(@RequestBody Map<String, String> credentials){
		
		return ResponseEntity.ok( "Successful authenticated with JWT." );
	}
	
	
	@GetMapping("/access")
	ResponseEntity<?> access(){
		
		return ResponseEntity.ok( "JWT valid!" );	
	}
}
