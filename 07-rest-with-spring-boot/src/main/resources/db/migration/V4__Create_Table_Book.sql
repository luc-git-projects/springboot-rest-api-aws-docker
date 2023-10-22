CREATE TABLE book (
    id BIGINT NOT NULL AUTO_INCREMENT,
    isbn VARCHAR(255),
    launch_date DATE,
    price DECIMAL(38,2),
    title VARCHAR(255),
    PRIMARY KEY (id)
) engine=InnoDB