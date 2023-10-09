CREATE TABLE person(
    id         bigint(20) NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(80),
    last_name  VARCHAR(80),
    address    VARCHAR(255),
    gender     VARCHAR(10),
    PRIMARY KEY (id)
);