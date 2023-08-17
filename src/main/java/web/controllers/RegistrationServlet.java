package web.controllers;

import CustomException.UserExistException;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import service.SessionService;
import service.UserService;

import java.io.Writer;
import java.util.UUID;

@Slf4j
public class RegistrationServlet implements MainController {

    private final UserService userService = new UserService();
    private final SessionService sessionService = new SessionService();

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());

        if (webExchange.getRequest().getMethod().equalsIgnoreCase("post")) {
            log.info("Post handler /registration -> start");

            String login = webExchange.getRequest().getParameterValue("login");
            String password = webExchange.getRequest().getParameterValue("password");
            log.info("Registration login -> {}", login);

            User user = null;
            if (login != null && password != null) {
                try {
                    user = userService.insert(login, password);
                } catch (UserExistException e) {
                    webExchange.setAttributeValue("errorMessage", e.getMessage());
                    log.info("Duplicate login");
                }
            }
            log.info("Post handler /registration user -> {} successful", login);

            UUID uuid = sessionService.insert(user);

            Cookie cookie = new Cookie("sessionId", uuid.toString());



        }

        templateEngine.process("registration", ctx, writer);
    }
}
