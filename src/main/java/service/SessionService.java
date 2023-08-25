package service;

import CustomException.IsNotValidSessionException;
import dao.SessionDao;
import dao.UserDao;
import model.Session;
import model.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class SessionService {

    private final SessionDao sessionDao;

    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public SessionService() {
        this.sessionDao = new SessionDao();
    }

    private final int sessionAlive = 360;

    public UUID insert(User user) {
        UUID uuid = UUID.randomUUID();
        Session session = new Session(uuid, user, LocalDateTime.now().plusMinutes(sessionAlive));
        sessionDao.insert(session);
        return uuid;
    }

    public Session getValidSessionById(UUID uuid) throws IsNotValidSessionException {
        Optional<Session> optionalSession = sessionDao.getById(uuid);
        if (optionalSession.isEmpty()) {
            throw new IsNotValidSessionException("Session does not exist");
        }
        Session session = optionalSession.get();
        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IsNotValidSessionException("Session expired");
        }

        return session;
    }

    public void delete(String id) {
        sessionDao.deleteById(UUID.fromString(id));
    }
}
