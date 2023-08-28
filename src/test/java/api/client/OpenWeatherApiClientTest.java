package api.client;

import CustomException.OpenApiWeatherErrorException;
import api.dto.*;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@WireMockTest(httpPort = 8089)
@Slf4j
class OpenWeatherApiClientTest {

    OpenWeatherApiClient apiClient = new OpenWeatherApiClient();

    @Test
    void getLocationByCity_success() throws URISyntaxException, IOException, InterruptedException, OpenApiWeatherErrorException {

        apiClient.GEO_HOST = "http://localhost:8089";
        stubFor(get(ConfigApiTest.LOCATION_URL_MOSCOW).willReturn(ok().withBody(ConfigApiTest.LOCATION_ANSWER_MOSCOW)));

        LocationDto locationDtoExpected = new LocationDto();
        locationDtoExpected.setName("Moscow");
        locationDtoExpected.setLatitude(55.7504461);
        locationDtoExpected.setLongitude(37.6174943);
        locationDtoExpected.setCountry("RU");
        locationDtoExpected.setState("Moscow");

        LocationDto locationDtoActual = apiClient.getLocationByCity("Moscow")[0];

        assertThat(locationDtoActual).usingRecursiveComparison().ignoringFields("localNames").isEqualTo(locationDtoExpected);
    }

    @Test
    void getForecast_success() throws URISyntaxException, IOException, InterruptedException {

        apiClient.FORECAST_HOST = "http://localhost:8089";
        stubFor(get(ConfigApiTest.FORECAST_URL_MOSCOW).willReturn(ok().withBody(ConfigApiTest.FORECAST_ANSWER_MOSCOW)));

        ForecastDto forecastDtoExpected = new ForecastDto();
        Weather weather = new Weather(800, "Clear", "clear sky", "01d");
        List<Weather> weathers = new ArrayList<>();
        weathers.add( weather);
        Main main = new Main(20.38f, 1011, 41);
        Wind wind = new Wind(2.03f, 40, 2.41f);
        Clouds clouds = new Clouds(5);
        Sys sys = new Sys("RU", 1693189547, 1693240591);
        forecastDtoExpected.setWeatherList(weathers);
        forecastDtoExpected.setMain(main);
        forecastDtoExpected.setVisibility(10000);
        forecastDtoExpected.setWind(wind);
        forecastDtoExpected.setClouds(clouds);
        forecastDtoExpected.setDt(1693221598);
        forecastDtoExpected.setSys(sys);
        forecastDtoExpected.setTimezone(10800);
        forecastDtoExpected.setCity("Moscow");

        ForecastDto forecastDtoActual = null;
        try {
            forecastDtoActual = apiClient.getForecast(55.7504461, 37.6174943);
        } catch (OpenApiWeatherErrorException e) {
            throw new RuntimeException(e);
        }

        assertThat(forecastDtoActual).usingRecursiveComparison().isEqualTo(forecastDtoExpected);
    }
}