package web.controllers;

import lombok.extern.log4j.Log4j;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;

@Log4j
public class RegistrationServlet implements MainController {

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());

        if (webExchange.getRequest().getMethod().equals("POST")) {
            log.info("Post handler /registration -> start");

            String login = webExchange.getRequest().getParameterValue("login");
            String password = webExchange.getRequest().getParameterValue("password");
            String repeatPassword = webExchange.getRequest().getParameterValue("repeat-password");
            log.info("form data: login = " + login + " // password = " + password + " // repeat-password = " + repeatPassword);



        }

        templateEngine.process("registration", ctx, writer);
    }
}
