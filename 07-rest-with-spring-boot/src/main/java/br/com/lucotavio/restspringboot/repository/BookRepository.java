package br.com.lucotavio.restspringboot.repository;

import br.com.lucotavio.restspringboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainsIgnoreCase(String title);

    List<Book> findByAuthorContainsIgnoreCase(String author);

}