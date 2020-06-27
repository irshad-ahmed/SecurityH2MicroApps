package com.fresco.patient;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import com.fresco.patient.service.ApiService;

@TestMethodOrder(Alphanumeric.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("unchecked")
class ApiServiceTest {
	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private ApiService apiService = new ApiService();

	@MockBean
	private ApiService mockService;

	@Autowired
	MockMvc mvc;

	protected String randString(Random rnd, StringBuilder salt, int n) {
		while (salt.length() < n)
			salt.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (rnd.nextFloat() * 26)));
		return salt.toString();
	}

	protected String randMobile(Random rnd, StringBuilder salt) {
		while (salt.length() < 10)
			salt.append("0123456789".charAt((int) (rnd.nextFloat() * 10)));
		return salt.toString();
	}

	static String mobile, password, token;

	@Test
	public void test1_register() {
		try {
			JSONObject json = new JSONObject();
			mobile = randMobile(new Random(), new StringBuilder());
			password = randString(new Random(), new StringBuilder(), 10);
			json.put("mobile", mobile);
			json.put("password", password);
			json.put("fullname", "Davy Jones");
			json.put("location", "Caribbean");
			mvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON)
					.content(json.toString())).andExpect(MockMvcResultMatchers.jsonPath("$.status", is("success")));
			mvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON)
					.content(json.toString())).andExpect(MockMvcResultMatchers.jsonPath("$.status", is("failed")));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test2_login() {
		try {
			JSONObject json = new JSONObject();
			json.put("mobile", mobile);
			json.put("password", randString(new Random(), new StringBuilder(), 10));
			mvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON)
					.content(json.toString())).andExpect(MockMvcResultMatchers.jsonPath("$.status", is("failed")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.message", is("invalid credentials")));
			json.put("password", password);
			MvcResult result = mvc
					.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON)
							.content(json.toString()))
					.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("success")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.token", notNullValue())).andReturn();
			JSONParser parser = new JSONParser();
			json = (JSONObject) parser.parse(result.getResponse().getContentAsString());
			token = json.get("token").toString();
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test3_search() {
		try {
			JSONArray arr = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("name", randString(new Random(), new StringBuilder(), 10));
			arr.add(json);
			Mockito.when(restTemplate.getForObject("http://localhost:8001/search", JSONArray.class)).thenReturn(arr);
			Assert.assertEquals(arr.toJSONString(), apiService.search().toJSONString());
			Mockito.when(mockService.search()).thenReturn(arr);
			mvc.perform(MockMvcRequestBuilders.get("/api/search").header("Authorization", ""))
					.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("failed")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.message", is("no jwt token found")));
			String dummy = randString(new Random(), new StringBuilder(), token.length()); 
			mvc.perform(MockMvcRequestBuilders.get("/api/search").header("Authorization", "Bearer " + dummy))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("failed")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message", is("invalid jwt token")));
			
			mvc.perform(MockMvcRequestBuilders.get("/api/search").header("Authorization", "Bearer " + token))
					.andExpect(MockMvcResultMatchers.content().string(arr.toJSONString()));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test4_bookSlot() {
		try {
			JSONObject json = new JSONObject();
			json.put("name", randString(new Random(), new StringBuilder(), 10));
			int hospId = new Random().nextInt(100 - 10) + 10;
			String date = "12-10-2020";
			Mockito.when(restTemplate.getForObject(
					"http://localhost:8001/book-slot/" + hospId + "/" + date + "/" + mobile, JSONObject.class))
					.thenReturn(json);
			Assert.assertEquals(json.toJSONString(), apiService.bookSlot(hospId, date, mobile).toJSONString());
			Mockito.when(mockService.bookSlot(hospId, date, mobile)).thenReturn(json);
			mvc.perform(MockMvcRequestBuilders.get("/api/book-slot/" + hospId + "/" + date).header("Authorization",
					"Bearer " + token)).andExpect(MockMvcResultMatchers.content().string(json.toJSONString()));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}
	@Test
	public void test5_checkStatus() {
		try {
			JSONObject json = new JSONObject();
			json.put("name", randString(new Random(), new StringBuilder(), 10));
			Mockito.when(restTemplate.getForObject(
					"http://localhost:8001/check-status/" + mobile, JSONObject.class))
					.thenReturn(json);
			Assert.assertEquals(json.toJSONString(), apiService.checkStatus(mobile).toJSONString());
			Mockito.when(mockService.checkStatus(mobile)).thenReturn(json);
			mvc.perform(MockMvcRequestBuilders.get("/api/check-status").header("Authorization",
					"Bearer " + token)).andExpect(MockMvcResultMatchers.content().string(json.toJSONString()));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}
}
