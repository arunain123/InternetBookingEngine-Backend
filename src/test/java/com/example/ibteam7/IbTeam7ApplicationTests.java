package com.example.ibteam7;

import com.example.ibteam7.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IbTeam7ApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Test
	void contextLoads() {
	}

	@Test
	void testHealth() throws Exception {
		HealthControllerTest.testHealth(mockMvc);
	}

	@Test
	void testGetPropertyList() throws Exception {
		RoomControllerTest.testGetPropertyList(mockMvc);
	}

	@Test
	void testGetMinimumNightlyRates() throws Exception {
		RoomControllerTest.testGetMinimumNightlyRates(mockMvc);
	}


	@Test
	void testPostDates() throws Exception {
		RoomDetailsControllerTest roomDetailsControllerTest = new RoomDetailsControllerTest();
		roomDetailsControllerTest.testPostDates();
	}

	@Test
	void testBaseURL() throws Exception {
		NonVersionControllerTest.testBaseURL(mockMvc);
	}

	@Test
	void getConfig() throws Exception {
		ConfigLoadControllerTest.getConfig(mockMvc);
	}

	@Test
	void testGetPromoCodes() throws Exception {
		PromotionsControllerTest.testGetPromoCodes(mockMvc);
	}

	@Test
	void testValidatePromoCode() throws Exception {
		PromotionsControllerTest.testValidatePromoCode(mockMvc);
	}

	@Test
	void testHealth2() throws Exception {
		HealthControllerTest.testHealth2(mockMvc);
	}

	@Test
	void validatetravelerinfo() throws Exception {
		FormFieldsValidationControllerTest.testTravelerInfoValidation(mockMvc);
	}

}