package com.auth.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.auth.utils.JwtUtil;

@Component
public class CustomJwtFilter extends OncePerRequestFilter{
	
	 @Autowired
	 private JwtUtil jwtUtil;
	 
	 @Autowired
	 private UserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		String header = request.getHeader("auth");
		
		String username = null;
		
		String jwt = null;
		
		if(header != null && header.startsWith("Bearer ")) {
			jwt  = header.substring(7);
			username = this.jwtUtil.extractUsername(jwt);
		}
		
		 if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			 
			  UserDetails user = this.customUserDetailsService.loadUserByUsername(username);
			  
			  if(this.jwtUtil.validateToken(jwt, user)) {
				  
				  UsernamePasswordAuthenticationToken upat
				     = new UsernamePasswordAuthenticationToken(user, null , new ArrayList<>());
				  
				   upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				   
				   SecurityContextHolder.getContext().setAuthentication(upat);
				  
			  }
			 
		 }
		
			filterChain.doFilter(request, response);
		
	}
	

}
