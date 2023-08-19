package web.controllers;

import CustomException.UserExistException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import model.User;
import service.SessionService;
import service.UserService;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebServlet(urlPatterns = "/signup")
public class SignUpServlet extends BaseServlet {

    private final UserService userService = new UserService();
    private final SessionService sessionService = new SessionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super.processTemplate("signup", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login").trim();
        String password = req.getParameter("password").trim();

        User user = null;
        if (!login.isEmpty() && !password.isEmpty()) {
            try {
                user = userService.insert(login, password);
            } catch (UserExistException e) {
                req.setAttribute("errorLogin", e.getMessage());
                super.processTemplate("signup", req, resp);
            }
        } else {
            req.setAttribute("errorLogin", "Login and password must be not empty");
            super.processTemplate("signup", req, resp);
        }

        UUID uuid = sessionService.insert(user);

        Cookie cookie = new Cookie(AUTH_COOKIE_NAME, uuid.toString());
        resp.addCookie(cookie);

        resp.sendRedirect("/");
    }
}
