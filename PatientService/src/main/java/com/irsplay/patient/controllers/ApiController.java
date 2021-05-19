package com.irsplay.patient.controllers;

import com.irsplay.patient.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
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

	@Autowired
	private ApiService apiService;

	@GetMapping("/search")
    public ResponseEntity<?> getAllHospitals(){
		System.out.println("In search");
		return ResponseEntity.ok(apiService.search());
	}

	@GetMapping("/book-slot/{hospitalId}/{date}")
	public ResponseEntity<?> getBookHospitalSlot(@PathVariable("hospitalId") Long hospitalId,@PathVariable("date") String date){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(apiService.bookSlot(hospitalId,date,name));


	}

	@GetMapping("/check-status")
	public ResponseEntity<?> getAppointmentStatus(){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(apiService.checkStatus(name));
	}

}
