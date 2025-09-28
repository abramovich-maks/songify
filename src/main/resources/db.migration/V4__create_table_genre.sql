CREATE TABLE genre
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_on TIMESTAMP WITH TIME ZONE DEFAULT now(),
    uuid       UUID DEFAULT uuid_generate_v4() NOT NULL UNIQUE
);