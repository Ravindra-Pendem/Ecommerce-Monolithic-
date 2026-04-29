package com.app.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecom.model.User;

@Service
public class UserService {

	private List<User> userList = new ArrayList<User>();
	
	public List<User> fetchAllUsers(){
		return userList;
	}
	
	public void addUser(User user){
		userList.add(user);
	}
	
	public Optional<User> fetchUserById(Long id) {
		
		return userList.stream()
				.filter(user -> user.getId().equals(id))
				.findFirst();
		
	}

	public User updateUser(Long id, User user) {
		
		for(User userDetails : userList) {
			if(userDetails.getId().equals(user.getId())) {
				userDetails = user;
				return userDetails;
			}
		}
		
		return userList.stream()
				.filter(userDetails -> userDetails.getId().equals(id))
				.findFirst()
				.map(existingUser -> 
					 {
						 existingUser.setFirstName(user.getFirstName());
						 existingUser.setLastName(user.getLastName());
						 return existingUser;
					 }
						)
				.orElseThrow();
		
	}
}
