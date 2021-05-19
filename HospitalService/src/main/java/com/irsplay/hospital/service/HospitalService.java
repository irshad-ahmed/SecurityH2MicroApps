package com.irsplay.hospital.service;

import com.irsplay.hospital.repo.HospitalRepository;
import com.irsplay.hospital.model.Hospital;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Value("${testcenter.service.url:http://localhost:8001/}")
    private String testCenterServiceUrl="http://localhost:8002/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HospitalRepository hospitalRepository;

    public JSONObject create(Hospital hospital) {
        /*
         * takes a hospital object and saves it in db and returns json response with a
         * single key 'status' and value 'success' if successful or 'failed' if
         * operation fails
         */
        Hospital response = hospitalRepository.save(hospital);
        String status = "failed";
        if (response != null) {
            status = "success";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        return jsonObject;
    }

    public JSONArray search() {
        /*
         * connects to database and returns all the hospitals in a json array
         */
        List<Hospital> hospitalList = hospitalRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(hospitalList);
        return jsonArray;
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
        String status = "failed";
        String message = null;
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(hospitalId);
        if (hospitalOptional.isPresent()) {
            Hospital hospital = hospitalOptional.get();
            if (hospital.getTestCenters().contains(testCenterId)) {
                message = "test center already added";
            } else {
                hospital.getTestCenters().add(testCenterId);
                hospitalRepository.save(hospital);
                status = "success";
            }
        } else {
            message = "hospital not found";
        }
        JSONObject jsonObject = new JSONObject();
        if (message != null) {
            jsonObject.put("message", message);
        }
        jsonObject.put("status", status);
        return jsonObject;
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
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(hospitalId);
        if (hospitalOptional.isPresent()) {
            String url= testCenterServiceUrl+"book-slot/"+hospitalId+"/"+date+"/"+user;
            return restTemplate.getForObject(url,JSONObject.class);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", "failed");
            jsonObject.put("message", "hospital not found");
            return jsonObject;
        }
    }

    public JSONObject checkStatus(String user) {
        /*
         * connect to TestCenterService to check the status of the booking done by the
         * user user return the json returned by the remote service.
         */
        String url= testCenterServiceUrl+"check-status/"+user;
        return restTemplate.getForObject(url,JSONObject.class);
    }
}
