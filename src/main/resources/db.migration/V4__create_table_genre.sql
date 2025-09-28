CREATE TABLE genre
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    uuid       UUID
);