package web.controllers;

import api.client.OpenWeatherApiClient;
import api.dto.LocationDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = "/search")
public class SearchServlet extends BaseServlet {

    private final OpenWeatherApiClient apiClient = new OpenWeatherApiClient();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String city = req.getParameter("q");
        if (city != null) {
            city = city.trim();
        }

        if (city != null && !city.isEmpty()) {
            List<LocationDto> locations = null;
            try {
                locations = Arrays.asList(apiClient.getLocationByCity(city));
            } catch (URISyntaxException | InterruptedException e) {
                e.fillInStackTrace();
            }
            if (locations != null) {
                log.info("Successful search response with locations -> {} items", locations.size());
                req.setAttribute("locations", locations);
            }
        }

        super.processTemplate("search", req, resp);
    }
}
