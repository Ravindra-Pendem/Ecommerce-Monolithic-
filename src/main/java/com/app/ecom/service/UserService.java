package com.app.ecom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserResponse> fetchAllUsers(){
		return userRepository.findAll().stream()
									.map(this::mapToUserResponse)
									.collect(Collectors.toList());
	}
	
	public void addUser(UserRequest userRequest){
		User user = new User();
		mapToUser(user, userRequest);
		userRepository.save(user);
	}
	
	public Optional<UserResponse> fetchUserById(Long id) {
		
		return userRepository.findById(id)
							 .map(this::mapToUserResponse);
	}

	public UserResponse updateUser(Long id, UserRequest userRequest) {
		
		User user =  userRepository.findById(id).orElseThrow();
		
		mapToUser(user, userRequest);
		userRepository.save(user);
		
		return mapToUserResponse(user);
		
	}
	
	private UserResponse mapToUserResponse(User user) {
		UserResponse response = new UserResponse();
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setEmail(user.getEmail());
		response.setPhone(user.getPhone());
		
		if(user.getAddress() != null) {
			AddressDTO address = new AddressDTO();
			address.setCity(user.getAddress().getCity());
			address.setState(user.getAddress().getState());
			address.setStreet(user.getAddress().getStreet());
			address.setCountry(user.getAddress().getCountry());
			address.setZipcode(user.getAddress().getZipcode());
			response.setAddress(address);
		}

		return response;
	}
	
	private User mapToUser(User user, UserRequest userRequest) {
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setEmail(userRequest.getEmail());
		user.setPhone(userRequest.getPhone());
		
		if(userRequest.getAddress() != null) {
			Address address = user.getAddress();
			if(address == null) {
				address = new Address();
			}
			address.setCity(userRequest.getAddress().getCity());
			address.setState(userRequest.getAddress().getState());
			address.setStreet(userRequest.getAddress().getStreet());
			address.setCountry(userRequest.getAddress().getCountry());
			address.setZipcode(userRequest.getAddress().getZipcode());
			user.setAddress(address);
		}
		
		return user;
	}
}
