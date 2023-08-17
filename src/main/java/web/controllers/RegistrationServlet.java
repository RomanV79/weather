package web.controllers;

import CustomException.UserExistException;
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
        System.out.println("### I am here -> GET ###");

        if (webExchange.getRequest().getMethod().equalsIgnoreCase("post")) {
            log.info("Post handler /registration -> start");
            System.out.println("### I am here -> POST ###");

            String login = webExchange.getRequest().getParameterValue("login");
            String password = webExchange.getRequest().getParameterValue("password");
            log.info("form data: login = " + login
                    + " // password = " + password);

            if (login != null && password != null) {
                try {
                    userService.insert(login, password);
                } catch (UserExistException e) {
                    // do something
                }
            }


        }

        templateEngine.process("registration", ctx, writer);
    }
}
