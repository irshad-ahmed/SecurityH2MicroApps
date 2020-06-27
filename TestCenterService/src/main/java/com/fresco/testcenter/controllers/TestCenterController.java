package com.fresco.testcenter.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
