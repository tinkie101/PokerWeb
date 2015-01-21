CREATE TABLE user(
    username VARCHAR(12) NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    PRIMARY KEY (username)
);