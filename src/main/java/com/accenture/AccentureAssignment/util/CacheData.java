package com.accenture.AccentureAssignment.util;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
/**
 * 
 * @author prajakta.kalpande
 * 
 * this class is used to store api data in cache
 * for 30 mins
 * It s a prototype because two objects are created 1 for Cases api data and another for vaccine api data.
 *
 */
@Scope(scopeName = "prototype")
public class CacheData {
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheData.class);
	private Cache<String, String> cache;

    //Constructor to build Cache Store
    public CacheData(int expiryDuration, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }
	
	 //Method to fetch previously stored record using record key
    public String get(String key) {
        return cache.getIfPresent(key);
    }

    
    //Method to put a new record in Cache Store with record key
    public void add(String key, String value) {
        if(key != null && value != null) {
            cache.put(key, value);
            LOGGER.info("Record stored in {} Cache with Key = {}", value.getClass().getSimpleName(), key);
        }
    }

}
