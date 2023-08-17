CREATE TABLE IF NOT EXISTS users
(
    id          INTEGER PRIMARY KEY,
    login       VARCHAR unique          NOT NULL,
    password    VARCHAR                 NOT NULL
);
-- CREATE UNIQUE INDEX users_unique_login_idx ON users (login);
CREATE SEQUENCE user_seq;


CREATE TABLE IF NOT EXISTS sessions
(
    id          INTEGER PRIMARY KEY,
    user_id     INTEGER NOT NULL,
    expires_at  TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX sessions_unique_user_id_idx ON sessions (user_id);


CREATE TABLE IF NOT EXISTS locations
(
    id          INTEGER PRIMARY KEY,
    name        VARCHAR NOT NULL,
    user_id     INTEGER NOT NULL,
    latitude    FLOAT NOT NULL ,
    longitude   FLOAT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX locations_unique_latitude_longitude_idx ON locations (latitude, longitude);
CREATE SEQUENCE locations_seq;