package com.deepa.weather.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Controller
public class SimpleController {
    @Value("${spring.application.name}")
    String appName;
    String minTemp;
    String maxTemp;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/weather")
    public String getWeather(Model model) throws IOException, InterruptedException, URISyntaxException {

        boolean useRestTemplate = true;

        if (useRestTemplate) {
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl
                    = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/eagan/today?unitGroup=us&include=current&key=GNZBTCFQ33PB7TXF5SDVHXMVC";
            ResponseEntity<String> response
                    = restTemplate.getForEntity(fooResourceUrl + "", String.class);

            DocumentContext jsonContext = JsonPath.parse(response.getBody());
            Double minTemp = jsonContext.read("$.days[0].tempmin");
            Double maxTemp = jsonContext.read("$.days[0].tempmax");

            model.addAttribute("minTemp", minTemp);
            model.addAttribute("maxTemp", maxTemp);
            return "weather_restTemplate";

        } else {

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
            Double minTemp = jsonContext.read("$.days[0].tempmin");
            Double maxTemp = jsonContext.read("$.days[0].tempmax");

            model.addAttribute("minTemp", minTemp);
            model.addAttribute("maxTemp", maxTemp);
            return "weather_httpClient";
        }
    }
}
