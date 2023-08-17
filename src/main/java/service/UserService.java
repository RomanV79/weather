package service;

import CustomException.UserExistException;
import com.password4j.Hash;
import com.password4j.Password;
import dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import model.User;

@Slf4j
public class UserService {

    private final UserDao userDao = new UserDao();

    public User insert(String login, String password) throws UserExistException {
        User user = new User();
        user.setLogin(login);
        String hash = Password.hash(password).withBcrypt().getResult();
        user.setPassword(hash);

        userDao.insert(user);
        log.info("Insert user -> {}", user);
        return user;
    }
}
