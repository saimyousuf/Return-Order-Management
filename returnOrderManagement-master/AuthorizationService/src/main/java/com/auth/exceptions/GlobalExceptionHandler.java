package com.auth.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth.helpers.APIExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private APIExceptionResponse response = new APIExceptionResponse();

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException unfe) {

		response.setError(true);
		response.setMessage(unfe.getMessage());

		return ResponseEntity.ok(response);

	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> handleAuthenticationException(AuthenticationException ae) {

		response.setError(true);
		response.setMessage("Wrong Password");

		return ResponseEntity.ok(response);

	}

}
