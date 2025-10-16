CREATE TABLE users
(
    id                 BIGSERIAL PRIMARY KEY,
    uuid               UUID                        NOT NULL DEFAULT uuid_generate_v4() UNIQUE,
    created_on         TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT now(),
    version            BIGINT                      NOT NULL DEFAULT 0,
    email              VARCHAR(255)                NOT NULL UNIQUE,
    password           VARCHAR(255)                NOT NULL,
    authorities        TEXT[],
    confirmation_token VARCHAR(255),
    enabled            BOOLEAN                     NOT NULL DEFAULT FALSE
);