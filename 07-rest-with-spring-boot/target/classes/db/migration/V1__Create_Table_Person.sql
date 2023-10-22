CREATE TABLE person (
    id BIGINT NOT NULL AUTO_INCREMENT,
    address VARCHAR(255),
    first_name VARCHAR(255),
    gender ENUM ('FEMALE','MALE'),
    last_name VARCHAR(255),
    PRIMARY KEY (id)
) engine=InnoDB