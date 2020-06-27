package com.fresco.hospital.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {
	/*
	 * create the following apis.
	 * 
	 * POST - /create - takes a Hospital entity in request body and creates a
	 * hospital in db.
	 * 
	 * GET - /search - returns all the hospitals in db as json array
	 * 
	 * GET - /add-test-center/{hospitalId}/{testCenterId} - adds the testCenterId to
	 * the list of testCenters for the hospital with passed hospitalId
	 * 
     * GET - /book-slot/{hospitalId}/{date}/{user} - takes hospitalId, date, user and books a
     * slot with supplied username by connecting to testcenter service.
     *
	 * /check-status/{user} checks the status of booked appointment of the user with
	 * username passed in by user variable
	 * 
	 * use service layer for bussiness logic.
	 */
}
