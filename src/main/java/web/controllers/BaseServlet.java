package web.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class BaseServlet extends HttpServlet {

    public static final String AUTH_COOKIE_NAME = "weather";
    private TemplateEngine templateEngine;

    @Override
    public void init() {

        ServletContext servletContext = this.getServletContext();

        WebApplicationTemplateResolver templateResolver =
                new WebApplicationTemplateResolver(JakartaServletWebApplication.buildApplication(servletContext));
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheTTLMs(3600000L);
        templateResolver.setCacheable(true);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    protected void processTemplate(String templateName, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        IWebExchange iWebExchange = JakartaServletWebApplication.buildApplication(this.getServletContext()).buildExchange(request, response);
        WebContext webContext = new WebContext(iWebExchange);

        templateEngine.process(templateName, webContext, response.getWriter());
    }

    protected Optional<Cookie> catchAuthCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(item -> item.getName().equals(AUTH_COOKIE_NAME)).findFirst();
    }
}
