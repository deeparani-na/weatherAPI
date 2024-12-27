package com.deepa.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class WeatherResponse {
    private String minTemp;
    private String maxTemp;

    public WeatherResponse(String minTemp, String maxTemp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }
}




