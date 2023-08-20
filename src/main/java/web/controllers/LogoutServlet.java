package web.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.SessionService;

import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends BaseServlet {
    private final SessionService sessionService = new SessionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<Cookie> authCookie = catchAuthCookie(req);
        authCookie.ifPresent(cookie -> sessionService.delete(cookie.getValue()));
        resp.sendRedirect("/signin");
    }
}
