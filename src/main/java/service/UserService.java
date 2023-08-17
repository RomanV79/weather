package service;

import CustomException.UserExistException;
import dao.UserDao;
import model.User;

public class UserService {

    private final UserDao userDao = new UserDao();

    public void insert(String login, String password) throws UserExistException {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        userDao.insert(user);
    }
}
