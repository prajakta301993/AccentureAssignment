package com.accenture.AccentureAssignment;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.accenture.AccentureAssignment.util.CacheData;

@SpringBootApplication
public class AccentureAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccentureAssignmentApplication.class, args);
	}
	
	//Bean for rest template
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	    return builder.setConnectTimeout(Duration.ofMillis(300000))
	     .setReadTimeout(Duration.ofMillis(300000)).build();
	}
	
	//Cache obj for cases
	@Bean
	@Qualifier("cases")
	public CacheData getCasesCache() {
		return new CacheData(5, TimeUnit.MINUTES);
	}
	
	//Cache obj for vaccine
	@Bean
	@Qualifier("vaccine")
	public CacheData getVaccineCache() {
		return new CacheData(5, TimeUnit.MINUTES);
	}
}
