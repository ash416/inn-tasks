CREATE TABLE person
(
    id         INT NOT NULL AUTO_INCREMENT,
    name       VARCHAR(250),
    birth_date VARCHAR(250)
);

CREATE TABLE user
(
    login       VARCHAR(250),
    password    VARCHAR(250)
);