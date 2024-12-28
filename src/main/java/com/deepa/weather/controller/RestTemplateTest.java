package com.deepa.weather.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/eagan/today?unitGroup=us&include=current&key=GNZBTCFQ33PB7TXF5SDVHXMVC";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + "", String.class);
        System.out.println(response);
    }






}
