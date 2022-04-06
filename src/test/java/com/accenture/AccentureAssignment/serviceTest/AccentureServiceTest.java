package com.accenture.AccentureAssignment.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.accenture.AccentureAssignment.service.AccentureService;
import com.google.gson.Gson;


@ExtendWith(MockitoExtension.class)
public class AccentureServiceTest {
	
	@InjectMocks
	private AccentureService serviceAssignment;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void calCorrelationCoefficent() {
		
		String casesResponse="{\"Afghanistan\": {\"All\": {\"confirmed\": 177897, \"recovered\": 0, \"deaths\": 7671, \"country\": \"Afghanistan\", \"population\": 35530081, \"sq_km_area\": 652090, \"life_expectancy\": \"45.9\", \"elevation_in_meters\": null, \"continent\": \"Asia\", \"abbreviation\": \"AF\", \"location\": \"Southern and Central Asia\", \"iso\": 4, \"capital_city\": \"Kabul\", \"lat\": \"33.93911\", \"long\": \"67.709953\", \"updated\": \"2022-04-05 04:20:48\"}}}"; 
		String vaccineResponse = "{\"Afghanistan\": {\"All\": {\"administered\": 5872684, \"people_vaccinated\": 4532577, \"people_partially_vaccinated\": 5188057, \"country\": \"Afghanistan\", \"population\": 35530081, \"sq_km_area\": 652090, \"life_expectancy\": \"45.9\", \"elevation_in_meters\": null, \"continent\": \"Asia\", \"abbreviation\": \"AF\", \"location\": \"Southern and Central Asia\", \"iso\": 4, \"capital_city\": \"Kabul\", \"updated\": \"2022/04/05 00:00:00+00\"}}}";

		
		HashMap<String, Map> casesJson = new Gson().fromJson(casesResponse, HashMap.class);
		HashMap<String, Map> vaccineJson = new Gson().fromJson(vaccineResponse, HashMap.class);
		assertEquals(-1.0, serviceAssignment.calCorrelationCoefficent(casesJson, vaccineJson));
		assertTrue(casesJson.containsKey("Afghanistan"));
		assertTrue(casesJson.get("Afghanistan").containsKey("All"));
		assertTrue(vaccineJson.containsKey("Afghanistan"));
		assertTrue(vaccineJson.get("Afghanistan").containsKey("All"));
		
		
	}
	
	@Test
	public void isValidContinent() {
		
		String continent = "Africa";
		//When parameter is not passed
		String nullContinent = null;
		String wrongContinent = "Australia";
		
		assertEquals(true, serviceAssignment.isValidContinent(continent));
		assertEquals(true, serviceAssignment.isValidContinent(nullContinent));
		assertEquals(false, serviceAssignment.isValidContinent(wrongContinent));
		
	}

}
