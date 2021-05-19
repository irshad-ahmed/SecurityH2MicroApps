package com.irsplay.hospital;

import static org.hamcrest.CoreMatchers.is;

import java.util.Optional;
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

import com.irsplay.hospital.model.Hospital;
import com.irsplay.hospital.repo.HospitalRepository;
import com.irsplay.hospital.service.HospitalService;

@TestMethodOrder(Alphanumeric.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("unchecked")
class HospitalServiceApplicationTests {
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

	static String hospitalName, user;
	static Long hospitalId, testCenterId;

	@Test
	public void test1_createAndSearch() {
		try {
			JSONObject json = new JSONObject();
			hospitalName = randString(new Random(), new StringBuilder());
			json.put("name", hospitalName);
			json.put("location", "Wakanda");
			mvc.perform(MockMvcRequestBuilders.post("/create").contentType(MediaType.APPLICATION_JSON)
					.content(json.toString())).andExpect(MockMvcResultMatchers.jsonPath("$.status", is("success")));
			MvcResult res = mvc.perform(MockMvcRequestBuilders.get("/search"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", is(hospitalName))).andReturn();
			JSONArray arr = (JSONArray) new JSONParser().parse(res.getResponse().getContentAsString());
			hospitalId = Long.parseLong(((JSONObject) arr.get(0)).get("id").toString());
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test2_addTestCenter() {
		try {
			Random rnd = new Random();
			testCenterId = rnd.nextLong();
			mvc.perform(MockMvcRequestBuilders.get("/add-test-center/" + rnd.nextLong() + "/" + testCenterId))
					.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("failed")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.message", is("hospital not found")));
			mvc.perform(MockMvcRequestBuilders.get("/add-test-center/" + hospitalId + "/" + testCenterId))
					.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("success")));
			mvc.perform(MockMvcRequestBuilders.get("/add-test-center/" + hospitalId + "/" + testCenterId))
					.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("failed")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.message", is("test center already added")));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test3_bookSlot() {
		try {
			Random rnd = new Random();
			user = randMobile(rnd, new StringBuilder());
			mvc.perform(MockMvcRequestBuilders.get("/book-slot/" + Math.abs(hospitalId - rnd.nextInt()) + "/02-10-2020/" + user))
					.andExpect(MockMvcResultMatchers.jsonPath("$.message", is("hospital not found")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.status", is("failed")));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}
}

@TestMethodOrder(Alphanumeric.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("unchecked")
class HospitalServiceApplicationMockTests {
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private HospitalRepository repo;

	@InjectMocks
	private HospitalService service = new HospitalService();

	@MockBean
	private HospitalService mockService;

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

	@Test
	public void test1_bookSlot() {
		try {
			JSONObject json = new JSONObject();
			user = randString(new Random(), new StringBuilder());
			json.put("user", user);
			Optional<Hospital> o = Optional.of(new Hospital());
			Mockito.when(repo.findById((long)1)).thenReturn(o);
			Mockito.when(
					restTemplate.getForObject("http://localhost:8002/book-slot/1/02-10-2020/" + user, JSONObject.class))
					.thenReturn(json);
			Assert.assertEquals(json.toJSONString(), service.bookSlot(1, "02-10-2020", user).toJSONString());
			Mockito.when(mockService.bookSlot(1, "02-10-2020", user)).thenReturn(json);
			mvc.perform(MockMvcRequestBuilders.get("/book-slot/1/02-10-2020/" + user))
					.andExpect(MockMvcResultMatchers.content().string(json.toJSONString()));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}
	@Test
	public void test2_checkStatus() {
		try {
			JSONObject json = new JSONObject();
			json.put("user", user);
			JSONObject response = restTemplate.getForObject("", JSONObject.class);
			Mockito.when(
					restTemplate.getForObject("http://localhost:8002/check-status/" + user , JSONObject.class))
					.thenReturn(json);
			Assert.assertEquals(json.toJSONString(), service.checkStatus(user).toJSONString());
			Mockito.when(mockService.checkStatus(user)).thenReturn(json);
			mvc.perform(MockMvcRequestBuilders.get("/check-status/" + user))
					.andExpect(MockMvcResultMatchers.content().string(json.toJSONString()));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}
}
