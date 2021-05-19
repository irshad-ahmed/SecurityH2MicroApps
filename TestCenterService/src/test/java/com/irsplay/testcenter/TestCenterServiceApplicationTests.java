package com.irsplay.testcenter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@TestMethodOrder(Alphanumeric.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestCenterServiceApplicationTests {
	@Autowired
	MockMvc mvc;

	protected String randString(Random rnd, StringBuilder salt) {
		while (salt.length() < 10)
			salt.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (rnd.nextFloat() * 26)));
		return salt.toString();
	}

	protected String randMobile(Random rnd, StringBuilder salt) {
		while (salt.length() < 10)
			salt.append("0123456789".charAt((int) (rnd.nextFloat() * 10)));
		return salt.toString();
	}

	static String user;
	static long hospitalId, testCenterId, bookingId;

	@Test
	public void test1_bookSlot() {
		try {
			hospitalId = new Random().nextLong();
			user = randMobile(new Random(), new StringBuilder());
			MvcResult res = mvc.perform(MockMvcRequestBuilders.get("/book-slot/" + hospitalId + "/02-10-2020/" + user))
					.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("PENDING")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.hospitalId", is(hospitalId)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.testCenterId", is(0)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.id", notNullValue())).andReturn();
			JSONObject json = (JSONObject) new JSONParser().parse(res.getResponse().getContentAsString());
			bookingId = Long.valueOf(json.get("id").toString());
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test2_getPendingBooking() {
		try {
			mvc.perform(MockMvcRequestBuilders.get("/get-pending-bookings/" + hospitalId))
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].status", is("PENDING")))
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].hospitalId", is(hospitalId)))
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].testCenterId", is(0)))
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(Long.valueOf(bookingId).intValue())));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test3_confirmBooking() {
		try {
			testCenterId = new Random().nextLong();
			mvc.perform(MockMvcRequestBuilders.get("/confirm-booking/" + bookingId + 1 + "/" + testCenterId))
					.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("failed")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.message", is("booking id not found")));

			MvcResult res = mvc
					.perform(MockMvcRequestBuilders.get("/confirm-booking/" + bookingId + "/" + testCenterId))
					.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("CONFIRMED")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.hospitalId", is(hospitalId)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.testCenterId", not(0)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(Long.valueOf(bookingId).intValue())))
					.andReturn();
			testCenterId = Long.valueOf(((JSONObject) new JSONParser().parse(res.getResponse().getContentAsString()))
					.get("testCenterId").toString());
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test4_checkStatus() {
		try {
			mvc.perform(MockMvcRequestBuilders.get("/check-status/" + user))
					.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("CONFIRMED")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.hospitalId", is(hospitalId)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.testCenterId", is(testCenterId)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(Long.valueOf(bookingId).intValue())));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}
}
