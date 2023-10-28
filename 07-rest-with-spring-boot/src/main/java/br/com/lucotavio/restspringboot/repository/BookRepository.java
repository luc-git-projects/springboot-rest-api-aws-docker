package br.com.lucotavio.restspringboot.repository;

import br.com.lucotavio.restspringboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}