package com.auth.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.auth.models.UserCredentials;

@Service
public class UserCredentialsService {
	
	private  List<UserCredentials> credentials = new ArrayList<>();
	
	public List<UserCredentials> getUsers(){
		
		UserCredentials user1 = new UserCredentials();
		user1.setUsername("user1");
		user1.setPassword("pass1");
		
		UserCredentials user2 = new UserCredentials();
		user2.setUsername("user2");
		user2.setPassword("pass2");
		
		
		UserCredentials user3 = new UserCredentials();
		user3.setUsername("user3");
		user3.setPassword("pass3");
		
		credentials.add(user1);
		credentials.add(user2);
		credentials.add(user3);
		
		return credentials;
		
	}
	

}
