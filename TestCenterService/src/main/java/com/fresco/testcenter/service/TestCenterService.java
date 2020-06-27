package com.fresco.testcenter.service;

import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TestCenterService {
	public JSONObject bookSlot(long hospitalId, String date, String user) throws ParseException {
		/*
		 * Takes the given arguments and creates a booking entry in the database and
		 * returns a json response of the booking record
		 */
		return null;
	}

	public JSONObject checkStatus(String user) {
		/*
		 * takes a username argument and checks if the user has any booking in database
		 * if yes then returns the booking object as json
		 * 
		 * if there is no booking entry for the user then return json with 'status'
		 * 'failed' and 'message' 'no bookings found'
		 */
		return null;
	}

	public JSONObject confirmBooking(long bId, long cId) {
		/*
		 * takes a booking id and test center id and checks if the booking is present in
		 * the database. if not present then return a json with 'status' 'failed' and
		 * 'message' 'booking not found'
		 * 
		 * if present then update the booking object status as 'CONFIRMED' and
		 * testCenterId as the passed testCenterId(cId)
		 * 
		 * save it to db and return the booking object as json.
		 */
		return null;
	}

	public JSONArray getPendingBookings(long hospitalId) {
		/*
		 * takes a hospitalId and returns all the pending bookings of that hospital in
		 * db as a jsonarray
		 */
		return null;
	}

}
