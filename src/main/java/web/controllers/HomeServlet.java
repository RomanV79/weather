package web.controllers;

import CustomException.IsNotValidSessionException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import model.Session;
import model.User;
import service.SessionService;
import service.UserService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@WebServlet(urlPatterns = "/")
public class HomeServlet extends BaseServlet {

    private final SessionService sessionService = new SessionService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isLoggedIn = false;

        Optional<Cookie> authCookie = catchAuthCookie(req, AUTH_COOKIE_NAME);
        if (authCookie.isPresent()) {
            UUID uuid = UUID.fromString(authCookie.get().getValue());
            try {
                Session session = sessionService.getValidSessionById(uuid);
                User user = session.getUser();
                isLoggedIn = true;
                req.setAttribute("user", user);
            } catch (IsNotValidSessionException e) {
                // do nothing
            }
        }

        req.setAttribute("isLoggedIn", isLoggedIn);
        super.processTemplate("home", req, resp);
    }
}
