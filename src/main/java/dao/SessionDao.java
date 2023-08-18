package dao;

import dao.util.PersistUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import model.Session;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SessionDao {

    private final EntityManager entityManager = PersistUtil.getEntityManager();

    public void insert (Session session) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        log.info("Start insert session");
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
        log.info("Inserted session successful");
    }

    public Optional<Session> getById(UUID id) {
        return Optional.ofNullable(entityManager.find(Session.class, id));
    }
}
