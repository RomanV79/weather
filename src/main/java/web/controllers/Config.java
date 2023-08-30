package web.controllers;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

@WebListener()
@Slf4j
public class Config implements ServletContextListener{

    public void contextInitialized(ServletContextEvent event) {

        String url = System.getenv("WEATHER_DB_HOST");
        String user = System.getenv("WEATHER_DB_USER");
        String password = System.getenv("WEATHER_DB_PASSWORD");

        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
        flyway.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
