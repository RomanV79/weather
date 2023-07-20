package repository.user;

import model.User;

public class DataJpaUserRepository implements UserRepository {

    JpaUserRepository jpaUserRepository;

    public DataJpaUserRepository(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public User getByLogin(String login) {
        return jpaUserRepository.getUserByLogin(login);
    }
}
