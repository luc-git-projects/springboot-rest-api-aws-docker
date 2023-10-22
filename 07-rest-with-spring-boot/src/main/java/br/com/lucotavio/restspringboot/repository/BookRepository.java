package br.com.lucotavio.restspringboot.repository;

import br.com.lucotavio.restspringboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
}