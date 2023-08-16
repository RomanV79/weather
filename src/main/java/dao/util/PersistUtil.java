package dao.util;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("weatherPersistence");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
