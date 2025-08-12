CREATE TABLE colors
(
    id         SERIAL PRIMARY KEY,
    color_name VARCHAR(50) NOT NULL
);

CREATE TABLE owners
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    birth_date DATE         NOT NULL
);

CREATE TABLE pets
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    birth_date DATE         NOT NULL,
    breed      VARCHAR(100) NOT NULL,
    color_id   INTEGER      NOT NULL,
    owner_id   INTEGER      NOT NULL,
    FOREIGN KEY (color_id) REFERENCES colors (id),
    FOREIGN KEY (owner_id) REFERENCES owners (id)
);

CREATE TABLE pet_friends
(
    pet_id    INTEGER NOT NULL,
    friend_id INTEGER NOT NULL,
    PRIMARY KEY (pet_id, friend_id),
    FOREIGN KEY (pet_id) REFERENCES pets (id),
    FOREIGN KEY (friend_id) REFERENCES pets (id)
);