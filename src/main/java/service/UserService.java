package service;

import CustomException.UserExistException;
import CustomException.UserNotFoundException;
import com.password4j.Hash;
import com.password4j.Password;
import dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import model.User;

import java.util.Optional;

@Slf4j
public class UserService {

    private final UserDao userDao = new UserDao();

    public User insert(String login, String password) throws UserExistException {
        User user = new User();
        user.setLogin(login);
        String hash = Password.hash(password).withBcrypt().getResult();
        user.setPassword(hash);

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
}
