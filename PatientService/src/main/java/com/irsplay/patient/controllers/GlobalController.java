package com.irsplay.patient.controllers;

import com.irsplay.patient.model.ApplicationUser;
import com.irsplay.patient.service.ApplicationUserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class GlobalController {

    @Autowired
    private ApplicationUserService applicationUserService;


    // create the following endpoints

    // POST api /register which will take ApplicationUser obj and registers the user
    // using service layer

    // POST api /login which will take ApplicationUser obj and authenticates the
    // user using service layer

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid ApplicationUser applicationUser) {
        JSONObject jsonObject = applicationUserService.register(applicationUser);
        return ResponseEntity.ok(jsonObject);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid ApplicationUser applicationUser) {
        JSONObject jsonObject = applicationUserService.login(applicationUser);
        return ResponseEntity.ok(jsonObject);
    }

}
