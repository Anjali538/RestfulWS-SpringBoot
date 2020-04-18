package com.java.restful.webservices.restfulwebServices.user;

import org.springframework.http.HttpStatus;
//Handling of Runtime exception using customization , it is specific to USER NOT FOUND
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	

}
