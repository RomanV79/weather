package web.controllers;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener()
@Slf4j
public class Config implements ServletContextListener{

    public void contextInitialized(ServletContextEvent event) {

        String flywayNameProp = "flyway.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(flywayNameProp);
        Properties properties = new Properties();

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = properties.getProperty("flyway.url");
        String user = properties.getProperty("flyway.user");
        String password = properties.getProperty("flyway.password");

        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
        flyway.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }


}
