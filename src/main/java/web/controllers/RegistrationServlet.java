package web.controllers;

import lombok.extern.slf4j.Slf4j;
import model.User;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import service.UserService;

import java.io.Writer;

@Slf4j
public class RegistrationServlet implements MainController {

    private final UserService userService = new UserService();

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());

        if (webExchange.getRequest().getMethod().equals("POST")) {
            log.info("Post handler /registration -> start");

            String login = webExchange.getRequest().getParameterValue("login");
            String password = webExchange.getRequest().getParameterValue("password");
            String repeatPassword = webExchange.getRequest().getParameterValue("repeat-password");
            log.info("form data: login = " + login
                    + " // password = " + password
                    + " // repeat-password = " + repeatPassword);

            if (login != null && password != null && repeatPassword != null) {
                if (password.equals(repeatPassword)) {
                    userService.insert(new User(login, password));
                }
            }



        }

        templateEngine.process("registration", ctx, writer);
    }
}
