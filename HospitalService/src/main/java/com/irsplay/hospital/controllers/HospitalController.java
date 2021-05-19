package com.irsplay.hospital.controllers;

import com.irsplay.hospital.model.Hospital;
import com.irsplay.hospital.service.HospitalService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	private HospitalService hospitalService;

	@PostMapping("/create")
	public ResponseEntity<?> createHospital(@RequestBody Hospital hospital){
		JSONObject response = hospitalService.create(hospital);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchHospital(){
		JSONArray response = hospitalService.search();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/add-test-center/{hospitalId}/{testCenterId}")
	public ResponseEntity<?> addTestCenter(@PathVariable("hospitalId") Long hospitalId,@PathVariable("testCenterId") Long testCenterId){
		JSONObject response = hospitalService.addTestCenter(hospitalId, testCenterId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/book-slot/{hospitalId}/{date}/{user}")
	public ResponseEntity<?> bookSlot(@PathVariable("hospitalId") Long hospitalId,@PathVariable("date") String date,@PathVariable("user") String user){
		JSONObject response = hospitalService.bookSlot(hospitalId, date, user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/check-status/{user}")
	public ResponseEntity<?> checkStatus(@PathVariable("user") String user){
		JSONObject response = hospitalService.checkStatus(user);
		response.toJSONString();

		return ResponseEntity.ok(response);

	}






	}
