package com.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth.helpers.APIResponse;
import com.auth.models.ChangePasswordModel;
import com.auth.services.ChangePasswordService;

@RestController
public class ChangePasswordController {
	
	@Autowired
	private ChangePasswordService changePasswordService;
	
	@RequestMapping(value="/changePassword" , method=RequestMethod.PUT)
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordModel changePasswordModel){
		  
		     String changePassword = this.changePasswordService.changePassword(changePasswordModel);
		     
		      APIResponse response  = new APIResponse();
		      response.setMessage(changePassword);
		     
		return new ResponseEntity<APIResponse>(response , HttpStatus.OK);
		  
	}

}
