package com.accenture.AccentureAssignment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.accenture.AccentureAssignment.service.AccentureService;
import com.accenture.AccentureAssignment.util.CacheData;

@ExtendWith(MockitoExtension.class)
public class AccentureControllerTest {
	
	
	@InjectMocks
	private AccentureController controller;
	
	@Mock
	private AccentureService serviceAssignment;
	
	@Mock
	private CacheData casesCacheData;
	
	@Mock
	private CacheData vaccineCacheData;
	
	
	
	@Test
	public void getCorelationCoefficentTest() {
		ResponseEntity badRequest= new ResponseEntity<>("Please enter valid Continent",HttpStatus.BAD_REQUEST);
		ResponseEntity rs= new ResponseEntity<>("0.0",HttpStatus.OK);
		
		String casesResponse="{\"Afghanistan\": {\"All\": {\"confirmed\": 177897, \"recovered\": 0, \"deaths\": 7671, \"country\": \"Afghanistan\", \"population\": 35530081, \"sq_km_area\": 652090, \"life_expectancy\": \"45.9\", \"elevation_in_meters\": null, \"continent\": \"Asia\", \"abbreviation\": \"AF\", \"location\": \"Southern and Central Asia\", \"iso\": 4, \"capital_city\": \"Kabul\", \"lat\": \"33.93911\", \"long\": \"67.709953\", \"updated\": \"2022-04-05 04:20:48\"}}}"; 
		String vaccineResponse = "{\"Afghanistan\": {\"All\": {\"administered\": 5872684, \"people_vaccinated\": 4532577, \"people_partially_vaccinated\": 5188057, \"country\": \"Afghanistan\", \"population\": 35530081, \"sq_km_area\": 652090, \"life_expectancy\": \"45.9\", \"elevation_in_meters\": null, \"continent\": \"Asia\", \"abbreviation\": \"AF\", \"location\": \"Southern and Central Asia\", \"iso\": 4, \"capital_city\": \"Kabul\", \"updated\": \"2022/04/05 00:00:00+00\"}}}";

		//When parameter is not passed
		String nullContinent = null;
		String wrongContinent = "Australia";
		when(serviceAssignment.isValidContinent(wrongContinent)).thenReturn(false);
		assertEquals(badRequest, controller.getCorelationCoefficent(wrongContinent));
		
		when(casesCacheData.get("All")).thenReturn(casesResponse);
		when(vaccineCacheData.get("All")).thenReturn(vaccineResponse);

		when(serviceAssignment.isValidContinent(nullContinent)).thenReturn(true);
		assertEquals(rs, controller.getCorelationCoefficent(nullContinent));
	}


}
