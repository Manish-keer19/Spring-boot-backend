package com.ms19.jourenal_apk.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ms19.jourenal_apk.weatherApiRes.WeatherApiRes;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeatherService {

    @Value("${WEATHER_API_KEY}")
    private  String apiKey;


    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherApiRes getWeather(String city) {
    
        log.info("api key is ",apiKey);
        // Replace placeholders in the URL
        String finalAPI = API.replace("API_KEY", apiKey).replace("CITY", city);

        // Make the API call
        ResponseEntity<WeatherApiRes> response = restTemplate.exchange(
                finalAPI,
                HttpMethod.GET,
                null,
                WeatherApiRes.class);

        // Return the body of the response
        return response.getBody();
    }
}
