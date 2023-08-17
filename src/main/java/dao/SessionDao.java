package dao;

import CustomException.UserExistException;
import dao.util.PersistUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import model.Session;
import model.User;
import org.hibernate.exception.ConstraintViolationException;

@Slf4j
public class SessionDao {

    private final EntityManager entityManager = PersistUtil.getEntityManager();

    public void insert (Session session) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            entityManager.persist(session);
            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.fillInStackTrace();
        }
    }

}
