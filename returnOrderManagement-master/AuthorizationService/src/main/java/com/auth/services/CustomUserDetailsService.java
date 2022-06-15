package com.auth.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.models.UserCredentials;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	  @Autowired 
	  private UserCredentialsService userCredentialsService;
	  
	  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		  List<UserCredentials> users = this.userCredentialsService.getUsers();
		  
		     
		  
		   for(UserCredentials credential : users) {
			   
			   if(credential.getUsername().equals(username)) {
				   User user = new User(credential.getUsername(),credential.getPassword(),new ArrayList<>());
				   return user;
			   }
		   }
		  
		throw new UsernameNotFoundException(username+" not found!");
		
	}

}
