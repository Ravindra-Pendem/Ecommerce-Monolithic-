package com.app.ecom.dto;

import lombok.Data;

@Data
public class UserResponse {

	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private AddressDTO address;
}
