package com.auth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.ValidatingDTO;
import com.auth.helpers.APIResponse;
import com.auth.helpers.JwtToken;
import com.auth.models.UserCredentials;
import com.auth.services.CustomUserDetailsService;
import com.auth.services.LoginService;
import com.auth.services.UserCredentialsService;
import com.auth.utils.JwtUtil;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private UserCredentialsService userCredentialsService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> generateJwt(@RequestBody UserCredentials userCredentials) throws Exception {

		JwtToken token = this.loginService.getToken(userCredentials);

		return ResponseEntity.ok(token);

	}

	@RequestMapping(value = "/noUsernameFound", method = RequestMethod.POST)
	public ResponseEntity<?> noUserNameFound(@RequestBody String username) {

		APIResponse response = new APIResponse();

		List<UserCredentials> users = this.userCredentialsService.getUsers();

		for (UserCredentials uc : users) {
			if (uc.getUsername().equals(username)) {
				response.setMessage("");
				return ResponseEntity.ok(response);
			}
		}

		response.setMessage(username + " does not exist");
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ResponseEntity<?> validateToken(@RequestHeader(name = "auth") String tokenDup) {
		ValidatingDTO validDTO = new ValidatingDTO();

		try {

			String token = tokenDup.substring(7);

			UserDetails userDetails = this.customUserDetailsService
					.loadUserByUsername(this.jwtUtil.extractUsername(token));
			if (this.jwtUtil.validateToken(token, userDetails)) {

				validDTO.setValidStatus(true);
				return ResponseEntity.ok(validDTO);
			} else {
				validDTO.setValidStatus(false);
				return new ResponseEntity<ValidatingDTO>(validDTO, HttpStatus.FORBIDDEN);
			}
		} catch (MalformedJwtException e) {
			
			  System.out.println("Getting caught");

			validDTO.setValidStatus(false);
			return new ResponseEntity<ValidatingDTO>(validDTO, HttpStatus.FORBIDDEN);

		}
		
		catch(SignatureException e) {
			System.out.println("Getting caught");
			validDTO.setValidStatus(false);
			return new ResponseEntity<ValidatingDTO>(validDTO, HttpStatus.FORBIDDEN);

		}
		
		catch(Exception e) {
			System.out.println("Getting caught");
			validDTO.setValidStatus(false);
			return new ResponseEntity<ValidatingDTO>(validDTO, HttpStatus.FORBIDDEN);

		}

	}

}
