create table author_has_book (
    id BIGINT NOT NULL AUTO_INCREMENT,
    author_id BIGINT,
    book_id BIGINT,
    PRIMARY KEY (id)
) engine=InnoDB