package web.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import service.LocationService;

import java.io.IOException;
import java.io.Writer;

@Slf4j
@WebServlet(urlPatterns = "/delete")
public class DeleteServlet extends BaseServlet {
    private final LocationService locationService = new LocationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Start Delete method -> /delete");
        String locationId = req.getParameter("location-id");
        log.info("Got location-id -> {}", locationId);
        locationService.deleteById(Long.parseLong(locationId));
        resp.sendRedirect("/home");
    }
}
