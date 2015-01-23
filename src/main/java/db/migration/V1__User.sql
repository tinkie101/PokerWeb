CREATE TABLE User(
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE Round(
    roundID int NOT NULL AUTO_INCREMENT,
    date TIMESTAMP NOT NULL,
    winner VARCHAR(255) NOT NULL,
    winnerNum int NOT NULL,
    PRIMARY KEY (roundID)
);

CREATE TABLE Game(
    gameID int NOT NULL AUTO_INCREMENT,
    hand VARCHAR(16) NOT NULL,
    evaluate VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    roundID int NOT NULL,
    PRIMARY KEY (gameID),
    CONSTRAINT fk_user FOREIGN KEY (username) REFERENCES User(username),
    CONSTRAINT fk_round FOREIGN KEY (roundID) REFERENCES Round(roundID)
);