package com.cesar.JWT.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	
	@GetMapping("/access")
	ResponseEntity<?> access(){
		
		return ResponseEntity.ok( "JWT valid!" );	
	}
}
