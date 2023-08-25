package service;

import CustomException.UserExistException;
import CustomException.UserNotFoundException;
import dao.UserDao;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest extends TestContainerConfig {

    EntityManager entityManager = emf.createEntityManager();
    UserDao userDao = new UserDao(entityManager);
    UserService userService = new UserService(userDao);
    @Test
    void user_insert_get_UserNotFoundException_UserExistException() throws UserExistException, UserNotFoundException {

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