package dao;

import CustomException.UserExistException;
import dao.util.PersistUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.UniqueConstraint;
import lombok.extern.slf4j.Slf4j;
import model.User;

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
        } catch (Exception e) {
            throw new UserExistException("Login already exist, try another one");
        }
    }
}
