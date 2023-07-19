package web.util;

import org.thymeleaf.web.IWebRequest;
import web.controllers.HomeServlet;
import web.controllers.LoginServlet;
import web.controllers.MainController;
import web.controllers.RegistrationServlet;

import java.util.HashMap;
import java.util.Map;

public class ControllerMappings {

    private static final Map<String, MainController> controllersByURL;

    static {
        controllersByURL = new HashMap<>();
        controllersByURL.put("/", new HomeServlet());
        controllersByURL.put("/login", new LoginServlet());
        controllersByURL.put("/registration", new RegistrationServlet());
    }

    public static MainController resolveControllerForRequest(final IWebRequest request) {
        final String path = request.getPathWithinApplication();
        return controllersByURL.get(path);
    }

    private ControllerMappings() {
        super();
    }
}
