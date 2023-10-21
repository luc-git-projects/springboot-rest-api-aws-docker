package br.com.lucotavio.restspringboot.repository;

import br.com.lucotavio.restspringboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorContainsIgnoreCase(String author);

    List<Book> findByTitleContainsIgnoreCase(String title);

    List<Book> findByLaunchDateBetween(LocalDate launchDateStart, LocalDate launchDateEnd);

    List<Book> findByPriceBetween(BigDecimal priceStart, BigDecimal priceEnd);

}