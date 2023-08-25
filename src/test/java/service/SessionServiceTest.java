package service;

import CustomException.IsNotValidSessionException;
import CustomException.UserExistException;
import dao.SessionDao;
import dao.UserDao;
import jakarta.persistence.EntityManager;
import model.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest extends TestContainerConfig {

    EntityManager entityManager = emf.createEntityManager();
    SessionDao sessionDao = new SessionDao(entityManager);
    SessionService sessionService = new SessionService(sessionDao);
    UserDao userDao = new UserDao(entityManager);
    UserService userService = new UserService(userDao);
    @Test
    void insertAndGetValidSession() throws UserExistException, IsNotValidSessionException {
        String login = "Tom";
        User user = userService.insert(login, "12345");
        UUID uuid = sessionService.createAndInsert(user);

        assertEquals(uuid, sessionService.getValidSessionById(uuid).getId());
    }

    @Test
    void getValidSessionById_NotExist() {
        UUID uuid = UUID.randomUUID();

        assertThrows(IsNotValidSessionException.class, () -> sessionService.getValidSessionById(uuid));

    }

    @Test
    void delete() throws UserExistException {
        String login = "John";
        User user = userService.insert(login, "12345");
        UUID uuid = sessionService.createAndInsert(user);
        sessionService.delete(uuid.toString());

        assertThrows(IsNotValidSessionException.class, () -> sessionService.getValidSessionById(uuid));
    }
}