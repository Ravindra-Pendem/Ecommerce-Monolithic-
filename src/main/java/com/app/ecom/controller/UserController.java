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

import com.app.ecom.model.User;
import com.app.ecom.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	private Long nextId = 1L;
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(userService.fetchAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		
		return userService.fetchUserById(id)
						 .map(ResponseEntity::ok)
						 .orElseGet(() -> ResponseEntity.notFound().build());
		
	}
	
	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody User user){
		user.setId(nextId++);
		userService.addUser(user);
		return ResponseEntity.ok().body("User Created Successfully.");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User user){
		
		return ResponseEntity.ok(userService.updateUser(id, user));	
	}
	
	
}
