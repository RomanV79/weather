package api.client;

import CustomException.OpenApiWeatherErrorException;
import api.dto.ForecastDto;
import api.dto.LocationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;


@Slf4j
public class OpenWeatherApiClient {

    String GEO_HOST = "http://api.openweathermap.org";
    String FORECAST_HOST = "https://api.openweathermap.org";
    final String GEO_URL = "/geo/1.0/direct";
    final String FORECAST_URL = "/data/2.5/weather";
    final String API_KEY = "86d23e100cbee2cce3ab1b963d3ff7bb";

    public LocationDto[] getLocationByCity(String city) throws OpenApiWeatherErrorException {

        final int limitCities = 10;

        String url = GEO_HOST + GEO_URL +
                "?q=" +
                city +
                "&limit=" +
                limitCities +
                "&appid=" +
                API_KEY;

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .timeout(Duration.of(1, ChronoUnit.SECONDS))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            throw new OpenApiWeatherErrorException("Forecast server doesn't answer");
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new OpenApiWeatherErrorException("Forecast server doesn't answer");
        }
        log.info("Get string for: city -> code: {} -> {}", city, response.statusCode());

        log.info("Response for city -> {} is -> {}", city, response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        LocationDto[] locationDto = new LocationDto[0];
        try {
            locationDto = objectMapper.readValue(response.body(), LocationDto[].class);
        } catch (JsonProcessingException e) {
            throw new OpenApiWeatherErrorException("Forecast server doesn't answer");
        }
        log.info("Successful convert to object; received for {} -> {} items", city, locationDto.length);

        return locationDto;
    }

    public ForecastDto getForecast(Double lat, Double lon) throws OpenApiWeatherErrorException, URISyntaxException {

        String url = FORECAST_HOST + FORECAST_URL +
                "?lat=" +
                lat +
                "&lon=" +
                lon +
                "&appid=" +
                API_KEY +
                "&units=metric";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.of(1, ChronoUnit.SECONDS))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new OpenApiWeatherErrorException("Forecast server doesn't answer");
        }
        log.info("Get string for response, code -> {}", response.statusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        ForecastDto forecastDto = null;
        try {
            forecastDto = objectMapper.readValue(response.body(), ForecastDto.class);
        } catch (JsonProcessingException e) {
            throw new OpenApiWeatherErrorException("Forecast server doesn't answer");
        }
        log.info("Successful convert to object; received -> {}", forecastDto);

        return forecastDto;
    }
}
