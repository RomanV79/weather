package repository.user;

import model.User;

public interface UserRepository {

    User save(User user);

    User getByLogin(String login);

}
