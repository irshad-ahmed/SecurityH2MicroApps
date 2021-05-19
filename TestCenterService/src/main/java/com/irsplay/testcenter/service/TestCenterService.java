package com.irsplay.testcenter.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.irsplay.testcenter.model.Booking;
import com.irsplay.testcenter.repo.BookingRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestCenterService {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");

    @Autowired
    private BookingRepository bookingRepository;

    public JSONObject bookSlot(long hospitalId, String strDate, String user) throws ParseException {
        /*
         * Takes the given arguments and creates a booking entry in the database and
         * returns a json response of the booking record
         */
        Date date = simpleDateFormat.parse(strDate);
        Booking booking = new Booking(hospitalId, user, date);
        Booking response = bookingRepository.save(booking);
        return response.toJsonObject();
    }

    public JSONObject checkStatus(String user) {
        /*
         * takes a username argument and checks if the user has any booking in database
         * if yes then returns the booking object as json
         *
         * if there is no booking entry for the user then return json with 'status'
         * 'failed' and 'message' 'no bookings found'
         */
        Booking response = bookingRepository.findByUser(user);
        JSONObject jsonObject;
        if (response == null) {
            jsonObject = new JSONObject();
            jsonObject.put("status", "failed");
            jsonObject.put("message", "no booking found");

        } else {
            jsonObject = response.toJsonObject();
        }
        return jsonObject;
    }

    public JSONObject confirmBooking(long bId, long cId) {
        /*
         * takes a booking id and test center id and checks if the booking is present in
         * the database. if not present then return a json with 'status' 'failed' and
         * 'message' 'booking not found'
         *
         * if present then update the booking object status as 'CONFIRMED' and
         * testCenterId as the passed testCenterId(cId)
         *
         * save it to db and return the booking object as json.
         */
        Booking booking = bookingRepository.findById(bId);
        JSONObject jsonObject;
        if (booking == null) {
            jsonObject = new JSONObject();
            jsonObject.put("status", "failed");
            jsonObject.put("message", "booking id not found");
        } else {
            booking.setStatus("CONFIRMED");
            booking.setTestCenterId(cId);
            booking = bookingRepository.save(booking);
            jsonObject = booking.toJsonObject();
        }
        return jsonObject;
    }

    public JSONArray getPendingBookings(long hospitalId) {
        /*
         * takes a hospitalId and returns all the pending bookings of that hospital in
         * db as a jsonarray
         */
        JSONArray jsonArray = new JSONArray();
        List<Booking> reponse = bookingRepository.findByHospitalIdAndStatus(hospitalId, "PENDING");
        if (reponse != null) {
            reponse.stream().forEach(booking -> jsonArray.add(booking.toJsonObject()));
        }
        return jsonArray;
    }

}
