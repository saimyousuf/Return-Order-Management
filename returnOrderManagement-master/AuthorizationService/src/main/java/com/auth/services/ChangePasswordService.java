package com.auth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.models.ChangePasswordModel;
import com.auth.models.UserCredentials;

@Service
public class ChangePasswordService {

	@Autowired
	private UserCredentialsService userCredentialsService;

	public String changePassword(ChangePasswordModel changePasswordModel) {

		List<UserCredentials> users = this.userCredentialsService.getUsers();

		String message = "";

		for (UserCredentials uc : users) {

			if (uc.getUsername().equals(changePasswordModel.getUsername())) {

				if (uc.getPassword().equals(changePasswordModel.getOldPassword())) {
					uc.setPassword(changePasswordModel.getNewPassword());
					message = "Password Changed";
					return message;
				} else {
					message = "Your old password does not match with the current password";
					return message;
				}

			}

		}

		return "Username does not exist";

	}

}
