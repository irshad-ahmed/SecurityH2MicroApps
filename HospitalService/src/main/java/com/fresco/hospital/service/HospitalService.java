package com.fresco.hospital.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fresco.hospital.model.Hospital;

@Service
public class HospitalService {
	public JSONObject create(Hospital hospital) {
		/*
		 * takes a hospital object and saves it in db and returns json response with a
		 * single key 'status' and value 'success' if successful or 'failed' if
		 * operation fails
		 */
		return null;
	}

	public JSONArray search() {
		/*
		 * connects to database and returns all the hospitals in a json array
		 */
		return null;
	}

	public JSONObject addTestCenter(long hospitalId, long testCenterId) {
		/*
		 * adds the testCenterId to the list of testCenters of hospital with hospitalId.
		 * return json object.
		 * 
		 * if hospitalId is not valid, return json with key 'status' as 'failed' and
		 * 'message' as 'hospital not found'
		 * 
		 * if hospital is found and testCenter is already added then return 'status' as
		 * 'failed' and 'message' as 'test center already added'
		 * 
		 * if hospital is found and testcenter is successfully added then return
		 * 'status' as 'success'
		 */
		return null;
	}

	public JSONObject bookSlot(long hospitalId, String date, String user) {
		/*
		 * find the hospital with hospitalId, if not found then return json response
		 * with 'status' 'failed' and 'message' 'hospital not found'
		 * 
		 * if hospital is found then connect to TestCenterService book a test
		 * appointment in hospital with hospitalId on date date for user user
		 * 
		 * return the json returned by the TestCenterService
		 */
		return null;
	}

	public JSONObject checkStatus(String user) {
		/*
		 * connect to TestCenterService to check the status of the booking done by the
		 * user user return the json returned by the remote service.
		 */
		return null;
	}
}
