package com.irsplay.patient.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    @Value("${hospital.service.url}")
    private String hospitalServiceUrl="http://localhost:8001/";

    @Autowired
    RestTemplate restTemplate;

    public JSONArray search() {
        // write code here to connect to HospitalService to search list of hospitals
        String url = hospitalServiceUrl + "search";
        return restTemplate.getForObject(url, JSONArray.class);
    }

    public JSONObject bookSlot(long hospitalId, String date, String user) {
        // write code here to connect to HospitalService to book an appointment in the
        // hospital identified by hospitalId on the given date for the user user
        String url = hospitalServiceUrl + "book-slot/" + hospitalId + "/" + date + "/" + user;
        return restTemplate.getForObject(url, JSONObject.class);
    }

    public JSONObject checkStatus(String name) {
        // write code here to connect to HospitalService to check the status of
        // appointment booked by the user user
        String url = hospitalServiceUrl + "check-status/" +name;
        return restTemplate.getForObject(url, JSONObject.class);
    }
}
