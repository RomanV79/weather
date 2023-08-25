package service;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

@Testcontainers
public class TestContainerConfig {

    static EntityManagerFactory emf;

    @Container
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15.4"
    ).withInitScript("db/migration/V1__init_db_create_tables.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        Map<String, String> config = new HashMap<>();

        String jdbcUrl = postgres.getJdbcUrl();
        String jdbcUser = postgres.getUsername();
        String jdbcPassword = postgres.getPassword();

        config.put("jakarta.persistence.jdbc.url", jdbcUrl);
        config.put("jakarta.persistence.jdbc.user", jdbcUser);
        config.put("jakarta.persistence.jdbc.password", jdbcPassword);

        emf = Persistence.createEntityManagerFactory("weatherPersist", config);
    }
}
