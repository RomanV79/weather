package web.controllers;

import CustomException.IsNotValidSessionException;
import CustomException.OpenApiWeatherErrorException;
import api.client.OpenWeatherApiClient;
import api.dto.LocationDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import model.Location;
import model.Session;
import model.User;
import service.LocationService;
import service.SessionService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@WebServlet(urlPatterns = "/search")
public class SearchServlet extends BaseServlet {
    private final OpenWeatherApiClient apiClient = new OpenWeatherApiClient();
    private final SessionService sessionService = new SessionService();
    private final LocationService locationService = new LocationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Start GET method -> /search");

        boolean isLoggedIn = false;

        Optional<Cookie> authCookie = catchAuthCookie(req);
        if (authCookie.isPresent()) {
            UUID uuid = UUID.fromString(authCookie.get().getValue());
            log.info("Got UUID from cookie -> {}", uuid.toString());
            try {
                Session session = sessionService.getValidSessionById(uuid);
                User user = session.getUser();
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("user", user);

                isLoggedIn = true;
                req.setAttribute("user", user);
                log.info("Session exist, get user -> {}", user);
            } catch (IsNotValidSessionException e) {
                log.info("SearchServlet -> IsNotValidSessionException");
                // do nothing
            }
        }
        
        String city = URLEncoder.encode(req.getParameter("q"), StandardCharsets.UTF_8);


        if (city != null && !city.isBlank()) {
            List<LocationDto> locationsApi = null;
            log.info("Start search throw API for city -> {}", city);
            try {
                locationsApi = Arrays.asList(apiClient.getLocationByCity(city));
            } catch (OpenApiWeatherErrorException e) {
                req.setAttribute("errorMessage", e.getMessage());
            }
            if (locationsApi != null) {
                log.info("Successful search response with locationsApi -> {} items", locationsApi.size());
                req.setAttribute("locations", locationsApi);
            }
        }

        req.setAttribute("isLoggedIn", isLoggedIn);
        super.processTemplate("search", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");
        String name = req.getParameter("name");
        log.info("Start POST method for subscribe city -> {}", name);
        log.info("Latitude -> {}", latitude);
        log.info("Longitude -> {}", longitude);
        log.info("Name -> {}", name);

        if (!latitude.isBlank() && !longitude.isBlank() && !name.isBlank()) {
            Double lat = Double.parseDouble(latitude);
            Double lon = Double.parseDouble(longitude);

            HttpSession httpSession = req.getSession();
            User user = (User) httpSession.getAttribute("user");
            log.info("Get User from httpSession successful -> {}", user);

            Location location = new Location();
            location.setName(name);
            location.setUser(user);
            location.setLatitude(lat);
            location.setLongitude(lon);
            locationService.insert(location);

            resp.sendRedirect("/home");
        }
    }
}
