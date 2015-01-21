CREATE TABLE User(
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE Round(
    roundID int NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (roundID)
);

CREATE TABLE Games(
    gameID int NOT NULL AUTO_INCREMENT,
    hand VARCHAR(16) NOT NULL,
    evaluate VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    round int NOT NULL,
    PRIMARY KEY (gameID),
    CONSTRAINT fk_user FOREIGN KEY (username) REFERENCES User(username),
    CONSTRAINT fk_round FOREIGN KEY (round) REFERENCES Round(roundID)
);