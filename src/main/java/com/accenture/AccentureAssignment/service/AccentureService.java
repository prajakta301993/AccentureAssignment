package com.accenture.AccentureAssignment.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccentureService {

	private static final Set<String> continentSet = new HashSet<>(
			Arrays.asList("Africa", "Asia", "Antarctica", "South America", "North America", "Oceania", "Europe", null));
	private static final Logger LOGGER = LoggerFactory.getLogger(AccentureService.class);

	/**
	 * Actual method to calculateCorrelationCoefficent 
	 * 
	 * @param casesJson
	 * @param vaccineJson
	 * @return corelationCoeficient
	 */
	public float calCorrelationCoefficent(HashMap<String, Map> casesJson, HashMap<String, Map> vaccineJson) {

		LOGGER.info("Calculating Correlation Coefficent");
		double sigmaX = 0, sigmaY = 0, sigmaXY = 0, sigmaXX = 0, sigmaYY = 0;
		int n = casesJson.size();
		float corelationCoefficient = 0;
		try {
			for (String key : casesJson.keySet()) {
				if (!vaccineJson.containsKey(key)) {
					n--;
					continue;
				}
				Map<String, Object> allCases = (Map<String, Object>) casesJson.get(key).get("All");
				Map<String, Object> allVaccines = (Map<String, Object>) vaccineJson.get(key).get("All");
				if (!allCases.containsKey("population") || !allCases.containsKey("deaths")
						|| !allVaccines.containsKey("people_vaccinated"))
					continue;

				double population = (double) allCases.get("population");
				double deaths = (double) allCases.get("deaths");
				double fullyVaccinated = (double) allVaccines.get("people_vaccinated");

				double X = deaths / population * 100;
				double Y = fullyVaccinated / population * 100;

				sigmaX += X;
				sigmaY += Y;
				sigmaXY += X * Y;
				sigmaXX += X * X;
				sigmaYY += Y * Y;

			}
			corelationCoefficient = (float) (n * sigmaXY - sigmaX * sigmaY)
					/ (float) (Math.sqrt((n * sigmaXX - sigmaX * sigmaX) * (n * sigmaYY - sigmaY * sigmaY)));
		} catch (ArithmeticException arthmaticExep) {
			LOGGER.info("Exception while calculatig the coeficient corelation");
			arthmaticExep.getMessage();
		} catch (Exception e) {
			LOGGER.info("Exception while calculatig the coeficient corelation");
			e.getMessage();
		}
		return corelationCoefficient;

	}

	/*
	 * Check from final static list if it contains Continent name
	 */
	public boolean isValidContinent(String continent) {
		if (continentSet.contains(continent))
			return true;
		else
			return false;
	}
}
