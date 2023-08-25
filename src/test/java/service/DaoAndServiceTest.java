package service;

import CustomException.UserExistException;
import CustomException.UserNotFoundException;
import dao.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
class DaoAndServiceTest {

    private static EntityManagerFactory emf;

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

    @Test
    void user_insert_get_UserNotFoundException_UserExistException() throws UserExistException, UserNotFoundException {
        EntityManager entityManager = emf.createEntityManager();
        UserDao userDao = new UserDao(entityManager);
        UserService userService = new UserService(userDao);

        String login = "Bob";

        // try to get a user that doesn't  exist in DB
        assertThrows(UserNotFoundException.class, () -> userService.getByLogin(login));

        // try to insert user in DB and get them after
        userService.insert(login, "12345");
        assertEquals(login, userService.getByLogin(login).getLogin());

        // try to insert user with existing login
        assertThrows(UserExistException.class, () -> userService.insert(login, "12345"));
    }
}