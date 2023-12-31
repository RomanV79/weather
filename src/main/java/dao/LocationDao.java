package dao;

import CustomException.LocationsNotFoundException;
import dao.util.PersistUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import model.Location;
import model.User;

import java.util.*;

@Slf4j
public class LocationDao {

//    private final EntityManager entityManager = PersistUtil.getEntityManager();
    private final EntityManager entityManager;

    public LocationDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public LocationDao() {
        this.entityManager = PersistUtil.getEntityManager();
    }

    public void insert (Location location) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        log.info("Start insert location -> {}", location);
        try {
            entityManager.persist(location);
            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.fillInStackTrace();
        }
        log.info("Inserted location successful");
    }

    public List<Location> getLocationsByUser(User user) throws LocationsNotFoundException {
        log.info("Start get locations by user_id -> {}", user.getId());
        String hql = "FROM Location WHERE user = :user";
        List<Location> locations = new ArrayList<>();
        try {
            locations = entityManager.createQuery(hql, Location.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (NoResultException e) {
            throw new LocationsNotFoundException("No locations found");
        }
        log.info("Get locations by login user_id -> {} successful, locations size -> {}", user.getId(), locations.size());
        return locations;
    }

    public void deleteById(Long id) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            log.info("Start delete location by Id");
            try {
                Location location = entityManager.find(Location.class, id);
                entityManager.remove(location);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.fillInStackTrace();
            }
            log.info("Delete location successful");
    }
}
