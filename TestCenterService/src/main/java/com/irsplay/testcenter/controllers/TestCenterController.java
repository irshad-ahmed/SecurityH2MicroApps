package com.irsplay.testcenter.controllers;

import com.irsplay.testcenter.service.TestCenterService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/")
public class TestCenterController {
	/*
	 * create the following apis.
	 *
	 * GET - /book-slot/{hospitalId}/{date}/{user} - create a booking entry with the
	 * hospitalId, date and username
	 *
	 * GET - /check-status/{user} checks the status of booked appointment of the
	 * user
	 *
	 * GET - /confirm-booking/{bId}/{cId} takes a booking id and testcenterid and
	 * confirms the booking with the given testcenter id
	 *
	 * GET - /get-pending-bookings/{hospitalId} - returns all the pending bookings
	 * of the hospital with hospitalId passed as pathvariable
	 *
	 * use service layer for bussiness logic.
	 */
	@Autowired
	private TestCenterService testCenterService;

	@GetMapping("/book-slot/{hospitalId}/{date}/{user}")
	public ResponseEntity<?> bookSlot(@PathVariable("hospitalId") Long hospitalId, @PathVariable("date") String date, @PathVariable("user") String user) throws ParseException {
		JSONObject response = testCenterService.bookSlot(hospitalId, date, user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/check-status/{user}")
	public ResponseEntity<?> checkStatus(@PathVariable("user") String user) {
		JSONObject response = testCenterService.checkStatus(user);
		return ResponseEntity.ok(response);

	}

	@GetMapping("/confirm-booking/{bId}/{cId}")
	public ResponseEntity<?> confirmBooking(@PathVariable("bId") long bookingId,@PathVariable("cId") long testcenterId) {
		return ResponseEntity.ok(testCenterService.confirmBooking(bookingId,testcenterId));

	}

	@GetMapping("/get-pending-bookings/{hospitalId}")
	public ResponseEntity<?> pendingBookings(@PathVariable("hospitalId") long hospitalId){
		return ResponseEntity.ok(testCenterService.getPendingBookings(hospitalId));
	}
}
