package com.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.helpers.JwtToken;
import com.auth.models.UserCredentials;
import com.auth.utils.JwtUtil;

@Service
public class LoginService {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	  public JwtToken getToken(UserCredentials userCredentials) throws AuthenticationException , UsernameNotFoundException {
		  
		    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
		        =  new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword());
		    
		    this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		    
		    UserDetails userDetails  = this.customUserDetailsService.loadUserByUsername(userCredentials.getUsername());
		    
		     String token = this.jwtUtil.generateToken(userDetails);
		     
		     JwtToken jwt  = new JwtToken();
		     
		       jwt.setToken(token);
		       
		       return jwt;

	  }

}
