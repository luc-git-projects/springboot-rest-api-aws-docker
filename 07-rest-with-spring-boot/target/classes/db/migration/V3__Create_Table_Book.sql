CREATE TABLE book (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100),
    author VARCHAR(100),
    launch_date DATE,
    price DECIMAL(38,2),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;