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
import service.LocationService;
import service.SessionService;
import web.dto.ConverterDto;
import web.dto.ForecastWebDto;

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
    private final ConverterDto converterDto = new ConverterDto();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Start GET method -> /home");
        boolean isLoggedIn = false;

        Optional<Cookie> authCookie = catchAuthCookie(req);
        if (authCookie.isPresent()) {
            UUID uuid = UUID.fromString(authCookie.get().getValue());
            log.info("Got UUID from cookie -> {}", uuid.toString());
            try {
                Session session = sessionService.getValidSessionById(uuid);
                User user = session.getUser();
                isLoggedIn = true;
                req.setAttribute("user", user);

                List<Location> locations = locationService.getLocationsByUser(user);
                log.info("Got locations, size -> {}", locations.size());
//                List<ForecastDto> forecastDtos = new ArrayList<>();
                List<ForecastWebDto> webDtos = new ArrayList<>();
                for (Location location: locations) {
                    ForecastDto dto = apiClient.getForecast(location.getLatitude(), location.getLongitude());
//                    forecastDtos.add(dto);
                    ForecastWebDto webDto = converterDto.fromForecastDtoToForecastWeb(dto);
                    webDto.setLocationId(location.getId());
                    webDtos.add(webDto);
                    log.info("Added to show WebDto -> {}", webDto);
                }
                log.info("Got forecasts, size -> {}", webDtos.size());
                req.setAttribute("forecasts", webDtos);
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
