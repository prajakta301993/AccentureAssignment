package com.accenture.AccentureAssignment.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.accenture.AccentureAssignment.service.AccentureService;
import com.accenture.AccentureAssignment.util.CacheData;
import com.google.gson.Gson;

@RestController
public class AccentureController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccentureController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private AccentureService serviceAssignment;

	@Autowired
	@Qualifier("cases")
	CacheData casesCacheData;

	@Autowired
	@Qualifier("vaccine")
	CacheData vaccineCacheData;

	@Value("${caseURL}")
	String caseUrl;

	@Value("${vaccineURL}")
	String vaccineUrl;

	
	@RequestMapping(value = "/getCorelationCoefficent")
	public ResponseEntity<String> getCorelationCoefficent(@RequestParam(required = false) String continent) {
		String casesResponse = null;
		String vaccineResponse = null;
		String  continentAll = null;
		float correlationCoefficient = 0 ;
		try {
			if(serviceAssignment.isValidContinent(continent)) {
				LOGGER.info("Continent is valid : {}", continent);
				correlationCoefficient = fetchDataFromJson(casesResponse, vaccineResponse , continentAll , continent);
				
			}else {
				
				return new ResponseEntity<>("Please enter valid Continent",HttpStatus.BAD_REQUEST);
			}
		}catch(NullPointerException e) {
			LOGGER.error(e.getMessage());
		}
		
		return new ResponseEntity<>(String.valueOf(correlationCoefficient),HttpStatus.OK);
	}

	/**
	 * 
	 * @param casesResponse
	 * @param vaccineResponse
	 * @param continentAll
	 * @param continent
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private float fetchDataFromJson(String casesResponse, String vaccineResponse, String continentAll,
			String continent) throws NullPointerException {
		if(continent == null ) {
			continentAll = "All";
		}else {
			continentAll = continent;
		}
		
		//If not present in cache
		if (casesCacheData.get(continentAll) == null || vaccineCacheData.get(continentAll) == null) {

			if (continent != null && continent != "") {
				Map<String, String> param = new HashMap<>();
				param.put("continent", continent);
				LOGGER.info("Fetching data from REST for Continent : "+ continent);
				casesResponse = restTemplate.getForObject(caseUrl.concat("?continent={continent}"), String.class,
						param);
				vaccineResponse = restTemplate.getForObject(vaccineUrl.concat("?continent={continent}"), String.class,
						param);

			} else {
				LOGGER.info("Fetching data from REST for all countries ");
				casesResponse = restTemplate.getForObject(caseUrl, String.class);
				vaccineResponse = restTemplate.getForObject(vaccineUrl, String.class);
			}
				if(casesResponse != null && vaccineResponse != null) {
				casesCacheData.add(continentAll, casesResponse);
				vaccineCacheData.add(continentAll, vaccineResponse);
				}
		} else {
			//Directly fetch if present in cache
			LOGGER.info("Fetching data from Cache");
			casesResponse = casesCacheData.get(continentAll);
			vaccineResponse = vaccineCacheData.get(continentAll);
		}
		
		HashMap<String, Map> casesJson = new Gson().fromJson(casesResponse, HashMap.class);
		HashMap<String, Map> vaccineJson = new Gson().fromJson(vaccineResponse, HashMap.class);
		return serviceAssignment.calCorrelationCoefficent(casesJson, vaccineJson);
		
	}

}
