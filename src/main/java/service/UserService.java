package service;

import CustomException.UserExistException;
import CustomException.UserNotFoundException;
import dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import model.User;
import util.PasswordUtil;

import java.util.Optional;

@Slf4j
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserService() {
        this.userDao = new UserDao();
    }


    public User insert(String login, String password) throws UserExistException {
        User user = new User();
        user.setLogin(login);
        user.setPassword(PasswordUtil.encryptPassword(password));

        userDao.insert(user);
        log.info("Insert user -> {}", user.getLogin());
        return user;
    }

    public User getById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userDao.getById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return optionalUser.get();
    }

    public User getByLogin(String login) throws UserNotFoundException {
        return userDao.getByLogin(login);
    }
}
