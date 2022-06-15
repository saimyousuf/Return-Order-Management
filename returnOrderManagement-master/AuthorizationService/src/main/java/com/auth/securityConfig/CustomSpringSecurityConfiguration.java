package com.auth.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth.filters.CustomJwtFilter;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
public class CustomSpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService customUserDetailsService;

	@Autowired
	private CustomJwtFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customUserDetailsService);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http

				.cors().disable().csrf().disable().authorizeRequests().antMatchers("/login").permitAll()
				.antMatchers("/noUsernameFound").permitAll()
				.anyRequest()
				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

}
