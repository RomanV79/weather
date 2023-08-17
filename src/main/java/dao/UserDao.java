package dao;

import dao.util.PersistUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import model.User;

@Slf4j
public class UserDao {

    private final EntityManager entityManager = PersistUtil.getEntityManager();

    public void insert (User user) {
        log.info("Start save user -> {}", user);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(user);
        entityManager.flush();

        transaction.commit();
        log.info("Success insert user");
    }
}
