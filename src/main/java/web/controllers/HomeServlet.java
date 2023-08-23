package web.controllers;

import CustomException.IsNotValidSessionException;
import CustomException.LocationsNotFoundException;
import api.client.OpenWeatherApiClient;
import api.dto.ForecastDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import model.Location;
import model.Session;
import model.User;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;
import service.LocationService;
import service.SessionService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@WebServlet(urlPatterns = "/")
public class HomeServlet extends BaseServlet {

    private final SessionService sessionService = new SessionService();
    private final LocationService locationService = new LocationService();
    private final OpenWeatherApiClient apiClient = new OpenWeatherApiClient();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Start GET method -> /home");
        boolean isLoggedIn = false;

        Optional<Cookie> authCookie = catchAuthCookie(req);
        if (authCookie.isPresent()) {
            UUID uuid = UUID.fromString(authCookie.get().getValue());
            try {
                Session session = sessionService.getValidSessionById(uuid);
                User user = session.getUser();
                isLoggedIn = true;
                req.setAttribute("user", user);

                List<Location> locations = locationService.getLocationsByUser(user);
                log.info("Got locations, size -> {}", locations.size());
                List<ForecastDto> forecastDtos = new ArrayList<>();
                for (Location location: locations) {
                    forecastDtos.add(apiClient.getForecast(location.getLatitude(), location.getLongitude()));
                }
                log.info("Got forecasts, size -> {}", forecastDtos.size());
                req.setAttribute("forecasts", forecastDtos);
            } catch (IsNotValidSessionException e) {
                // do nothing
            } catch (LocationsNotFoundException | URISyntaxException | InterruptedException e) {
                req.setAttribute("errorMessage", e.getMessage());
            }
        }

        req.setAttribute("isLoggedIn", isLoggedIn);
        super.processTemplate("home", req, resp);
    }
}
