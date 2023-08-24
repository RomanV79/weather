package web.controllers;

import CustomException.UserNotFoundException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import model.User;
import service.SessionService;
import service.UserService;
import util.PasswordUtil;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@WebServlet(urlPatterns = "/signin")
public class SignInServlet extends BaseServlet {

    private final UserService userService = new UserService();
    private final SessionService sessionService = new SessionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Start GET method -> /sign-in");
        super.processTemplate("signin", req, resp);
        log.info("Finish GET method -> /sign-in");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Start POST method -> /sign-in");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user;
        if (!login.isEmpty() && !password.isEmpty()) {
            login = login.trim();
            password = password.trim();
            try {
                user = userService.getByLogin(login);
                if (!PasswordUtil.isValidPassword(password, user.getPassword())) {
                    req.setAttribute("errorPassword", "Wrong password");
                    super.processTemplate("signin", req, resp);
                }
                UUID uuid = sessionService.createAndInsert(user);

                Cookie cookie = new Cookie(AUTH_COOKIE_NAME, uuid.toString());
                resp.addCookie(cookie);
                resp.sendRedirect("/home");

            } catch (UserNotFoundException e) {
                req.setAttribute("errorLogin", "User with login: " + login + " doesn't exist");
                super.processTemplate("signin", req, resp);
            }

        } else {
            req.setAttribute("errorLogin", "Login and password must be not empty");
            super.processTemplate("signin", req, resp);
        }
    }
}
