package api.test;

import api.client.OpenWeatherApiClient;
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
@WebServlet(urlPatterns = "/api/test/location")
public class TestLocation extends HttpServlet {

    private final OpenWeatherApiClient client = new OpenWeatherApiClient();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city");

        LocationDto[] location;
        try {
            location = client.getLocationByCity(city);
        } catch (URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Arrays.stream(location).forEach(System.out::println);
    }
}
