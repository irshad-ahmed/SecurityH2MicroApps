package com.irsplay.patient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sun.plugin.javascript.navig.Anchor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@Component
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HttpStatus httpStatus;
    private final Object responseBody;

    public ApiAuthenticationEntryPoint(HttpStatus httpStatus, Object responseBody) {
        Assert.notNull(httpStatus, "httpStatus cannot be null");
        Assert.notNull(responseBody, "responseBody cannot be null");
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
    }

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        //response.addHeader("WWW-Authenticate", "Basic realm=" );
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //PrintWriter writer = response.getWriter();
        //writer.println("HTTP Status 401 - " + authEx.getMessage());
       // response.setStatus(httpStatus.value());
        String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        //    username = jwtUtil.extractUsername(jwt);
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "failed");
        jsonObject.put("message", "no jwt token found");

        try (PrintWriter writer = response.getWriter()) {
            writer.print(new ObjectMapper().writeValueAsString(jsonObject));
        }
    }



}