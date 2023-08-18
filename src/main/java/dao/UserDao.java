package dao;

import CustomException.UserExistException;
import dao.util.PersistUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Optional;

@Slf4j
public class UserDao {

    private final EntityManager entityManager = PersistUtil.getEntityManager();

    public void insert (User user) throws UserExistException {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            entityManager.persist(user);
            entityManager.flush();

            transaction.commit();
        } catch (ConstraintViolationException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new UserExistException("Login already exist, try another one");
        }
    }

    public Optional<User> getById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }
}
