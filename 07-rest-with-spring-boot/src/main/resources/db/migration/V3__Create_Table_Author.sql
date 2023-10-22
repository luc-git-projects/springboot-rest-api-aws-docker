CREATE TABLE author (
    id BIGINT NOT NULL AUTO_INCREMENT,
    birth_date DATE,
    country VARCHAR(255),
    name VARCHAR(255),
    PRIMARY KEY (id)
) engine=InnoDB