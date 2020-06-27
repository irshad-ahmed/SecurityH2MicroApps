package com.fresco.patient.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	/*
	 * create the following apis. all these endpoints are prefixed with /api and are
	 * authenticated endpoints
	 * 
	 * GET - /search - connects to hospital service and returns all hospitals in
	 * json format.
	 * 
	 * GET - /book-slot/{hospitalId}/{date} - takes hospitalId and date and books a
	 * slot with logged in username by connecting to hospital service.
	 * 
	 * GET - /check-status checks the status of booked appointment of the user
	 * 
	 * use service layer for bussiness logic.
	 */
}
