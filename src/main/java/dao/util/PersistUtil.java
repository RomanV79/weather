package dao.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PersistUtil {
    private static final EntityManagerFactory EMF;

    static {
        Map<String, String> configOverrides = new HashMap<>();

        String url = System.getenv("WEATHER_DB_HOST");
        String user = System.getenv("WEATHER_DB_USER");
        String password = System.getenv("WEATHER_DB_PASSWORD");

        configOverrides.put("jakarta.persistence.jdbc.url", url);
        configOverrides.put("jakarta.persistence.jdbc.user", user);
        configOverrides.put("jakarta.persistence.jdbc.password", password);
        configOverrides.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");

        EMF = Persistence.createEntityManagerFactory("weatherPersist", configOverrides);
    }

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }
}
