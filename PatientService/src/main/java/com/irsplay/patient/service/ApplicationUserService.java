package com.irsplay.patient.service;

import com.irsplay.patient.config.JwtUtil;
import com.irsplay.patient.repo.ApplicationUserRepository;
import com.irsplay.patient.model.ApplicationUser;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private JwtUtil jwtUtil;

    public JSONObject register(ApplicationUser user) {
        // write code here to register the user user
        // return a json response with only one ky 'status'
        // if the registration is successful status is 'success' else 'failed'
        String status = "failed";
        Optional<ApplicationUser> exisitingUser = applicationUserRepository.findById(user.getMobile());
        if(!exisitingUser.isPresent()) {
            ApplicationUser response = applicationUserRepository.save(user);
            if (response != null) {
                status = "success";
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        return jsonObject;
    }

    public JSONObject login(ApplicationUser applicationUser) {
        // write code here to authenticate the user
        // return a json response with the following keys
        // if authentication is successful then key 'status' is 'success', 'token' is
        // json token, 'id' is userId
        // if authentication fails then key 'status' is failed and 'message' is 'invalid
        // credentials'
        JSONObject jsonObject = new JSONObject();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(applicationUser.getMobile(), applicationUser.getPassword()));
            UserDetails userDetails = userAuthService.loadUserByUsername(applicationUser.getMobile());
            String token = jwtUtil.generateToken(userDetails);
            jsonObject.put("status", "success");
            jsonObject.put("token", token);
            jsonObject.put("id", applicationUser.getMobile());
        } catch (BadCredentialsException e) {
            jsonObject.put("status", "failed");
            jsonObject.put("message", "invalid credentials");
        }
        return jsonObject;
    }
}
