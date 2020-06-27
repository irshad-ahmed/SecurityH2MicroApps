package com.fresco.patient.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
	public JSONArray search() {
		// write code here to connect to HospitalService to search list of hospitals
		return null;
	}

	public JSONObject bookSlot(long hospitalId, String date, String user) {
		// write code here to connect to HospitalService to book an appointment in the
		// hospital identified by hospitalId on the given date for the user user
		return null;
	}

	public JSONObject checkStatus(String name) {
		// write code here to connect to HospitalService to check the status of
		// appointment booked by the user user
		return null;
	}
}
