CREATE TABLE owners
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50)         NOT NULL,
    last_name  VARCHAR(50)         NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL
);

CREATE TABLE pets
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50) NOT NULL,
    age      INTEGER     NOT NULL CHECK (age >= 0 AND age <= 200),
    type     VARCHAR(50) NOT NULL,
    owner_id BIGINT,
    CONSTRAINT fk_pet_owner FOREIGN KEY (owner_id) REFERENCES owners (id) ON DELETE CASCADE
);

CREATE TABLE posts
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(50)    NOT NULL,
    description VARCHAR(100)   NOT NULL,
    price       NUMERIC(15, 2) NOT NULL CHECK (price >= 0),

    pet_id      BIGINT UNIQUE,
    owner_id    BIGINT,

    CONSTRAINT fk_post_pet FOREIGN KEY (pet_id) REFERENCES pets (id) ON DELETE SET NULL,
    CONSTRAINT fk_post_owner FOREIGN KEY (owner_id) REFERENCES owners (id) ON DELETE SET NULL
);

CREATE TABLE owner_roles
(
    owner_id BIGINT NOT NULL,
    roles    VARCHAR(255),
    FOREIGN KEY (owner_id) REFERENCES owners (id)
);

CREATE INDEX idx_pet_type ON pets (type);
CREATE INDEX idx_post_price ON posts (price);
CREATE INDEX idx_owner_email ON owners (email);