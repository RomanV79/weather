package api.test;

import api.client.OpenWeatherApiClient;
import api.dto.ForecastDto;
import api.dto.LocationDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@Slf4j
@WebServlet(urlPatterns = "/api/test/forecast")
public class TestForecast extends HttpServlet {

    private final OpenWeatherApiClient client = new OpenWeatherApiClient();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // latitude and longitude for Moscow
        Double lon = 37.6174943;
        Double lat = 55.7504461;

        ForecastDto forecastDto;
        try {
            forecastDto = client.getForecast(lat, lon);
        } catch (URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(forecastDto);
    }
}
