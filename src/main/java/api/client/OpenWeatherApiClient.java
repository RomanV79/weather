package api.client;

import api.dto.ForecastDto;
import api.dto.LocationDto;
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

    ObjectMapper objectMapper = new ObjectMapper();

    final String GEO_URL = "http://api.openweathermap.org/geo/1.0/direct";
    final String FORECAST_URL = "https://api.openweathermap.org/data/2.5/weather";
    final String API_KEY = "86d23e100cbee2cce3ab1b963d3ff7bb";

    public LocationDto[] getLocationByCity(String city) throws URISyntaxException, IOException, InterruptedException {

        final int limitCities = 10;

        String url = GEO_URL +
                "?q=" +
                city +
                "&limit=" +
                limitCities +
                "&appid=" +
                API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.of(1, ChronoUnit.SECONDS))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> location = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Get string for: city -> code: {} -> {}", city, location.statusCode());

        LocationDto[] locationDto = objectMapper.readValue(location.body(), LocationDto[].class);
        log.info("Successful convert to object; received {} -> {} items", city, locationDto.length);

        return locationDto;
    }

    public ForecastDto getForecast(Double lat, Double lon) throws URISyntaxException, IOException, InterruptedException {

        String url = FORECAST_URL +
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
        HttpResponse<String> forecast = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Get string for forecast, code -> {}", forecast.statusCode());

        ForecastDto forecastDto = objectMapper.readValue(forecast.body(), ForecastDto.class);
        log.info("Successful convert to object; received -> {}", forecastDto);

        return forecastDto;
    }
}
