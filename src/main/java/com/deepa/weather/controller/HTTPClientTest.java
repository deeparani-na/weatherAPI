package com.deepa.weather.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import static java.time.temporal.ChronoUnit.SECONDS;

public class HTTPClientTest {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/eagan/today?unitGroup=us&include=current&key=GNZBTCFQ33PB7TXF5SDVHXMVC"))
                .timeout(Duration.of(120, SECONDS))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(getRequest, HttpResponse.BodyHandlers.ofString());

        String responseJSON = response.body();

        DocumentContext jsonContext = JsonPath.parse(responseJSON);
        Double jsonpathCreatorName = jsonContext.read("$.days[0].tempmax");
        System.out.println(jsonpathCreatorName);

    }


}
