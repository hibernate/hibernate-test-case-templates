CREATE TYPE status AS ENUM (
    'ACTIVE',
    'INACTIVE'
);

CREATE TABLE sample
(
    id      BIGSERIAL NOT NULL,
    status  status NULL
);

CREATE TYPE compose_status AS ENUM (
    'ACTIVE',
    'INACTIVE'
);

CREATE TABLE compose_sample
(
    id      BIGSERIAL NOT NULL,
    compose_status compose_status NULL
);