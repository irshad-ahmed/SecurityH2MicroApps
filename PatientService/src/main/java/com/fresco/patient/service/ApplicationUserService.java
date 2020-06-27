package com.fresco.patient.service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fresco.patient.model.ApplicationUser;

@Service
public class ApplicationUserService {
	public JSONObject register(ApplicationUser user) {
		// write code here to register the user user
		// return a json response with only one ky 'status'
		// if the registration is successful status is 'success' else 'failed'
		return null;
	}

	public JSONObject login(ApplicationUser applicationUser) {
		// write code here to authenticate the user
		// return a json response with the following keys
		// if authentication is successful then key 'status' is 'success', 'token' is
		// json token, 'id' is userId
		// if authentication fails then key 'status' is failed and 'message' is 'invalid
		// credentials'
		return null;
	}
}
