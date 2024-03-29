DROP TABLE IF EXISTS users, categories, events, requests, compilations, event_compilations, comments CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id      BIGINT          GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name    VARCHAR(250)    NOT NULL,
    email   VARCHAR(255)    NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS categories (
    id      BIGINT          GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name    VARCHAR(50)     NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS locations (
    id      BIGINT          GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat     FLOAT           NOT NULL,
    lon     FLOAT           NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT           GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    annotation         VARCHAR(2000)    NOT NULL,
    category_id        BIGINT           REFERENCES categories (id) NOT NULL,
    confirmed_requests BIGINT,
    created_on         TIMESTAMP        WITHOUT TIME ZONE NOT NULL,
    description        VARCHAR(7000)    NOT NULL,
    event_date         TIMESTAMP        WITHOUT TIME ZONE NOT NULL,
    initiator_id       BIGINT           REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    location_id        BIGINT           REFERENCES locations (id) ON DELETE CASCADE,
    paid               BOOLEAN          NOT NULL,
    participant_limit  BIGINT,
    published_on       TIMESTAMP        WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state              VARCHAR          NOT NULL,
    title              VARCHAR(120)     NOT NULL,
    views              BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE,
    FOREIGN KEY (initiator_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests (
    id              BIGINT      GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    requester_id    BIGINT      REFERENCES users (id) ON DELETE CASCADE,
    event_id        BIGINT      REFERENCES events (id) ON DELETE CASCADE,
    created         TIMESTAMP   WITHOUT TIME ZONE,
    status          VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations (
    id              BIGINT          GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title           VARCHAR(50)    NOT NULL UNIQUE,
    pinned          BOOLEAN
);

CREATE TABLE IF NOT EXISTS event_compilations (
    id              BIGINT      GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    event_id        BIGINT      REFERENCES events (id) ON DELETE CASCADE,
    compilation_id  BIGINT      REFERENCES compilations (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments (
    id          BIGINT          GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text        VARCHAR(1024),
    author_id   BIGINT          REFERENCES users (id) ON DELETE CASCADE,
    event_id    BIGINT          REFERENCES events (id) ON DELETE CASCADE,
    created_on  TIMESTAMP       WITHOUT TIME ZONE
);