package service;

import dao.SessionDao;
import model.Session;
import model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionService {

    private final SessionDao sessionDao = new SessionDao();

    public UUID insert(User user) {
        UUID uuid = UUID.randomUUID();
        Session session = new Session(uuid, user, LocalDateTime.now());
        sessionDao.insert(session);
        return uuid;
    }
}
