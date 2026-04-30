package com.app.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers(){
		return ResponseEntity.ok(userService.fetchAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		
		return userService.fetchUserById(id)
						 .map(ResponseEntity::ok)
						 .orElseGet(() -> ResponseEntity.notFound().build());
		
	}
	
	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody UserRequest user){
		userService.addUser(user);
		return ResponseEntity.ok().body("User Created Successfully.");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,@RequestBody UserRequest user){
		
		return ResponseEntity.ok(userService.updateUser(id, user));	
	}
	
	
}
