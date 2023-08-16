package service;

import dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import model.User;

@Slf4j
public class UserService {

    private final UserDao userDao = new UserDao();

    public void insert(User user) {
        log.info("Start save to db, user -> {}", user);
        userDao.insert(user);
        log.info("User saved");
    }

}
