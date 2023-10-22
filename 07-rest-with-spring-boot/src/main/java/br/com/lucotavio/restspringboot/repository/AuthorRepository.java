package br.com.lucotavio.restspringboot.repository;

import br.com.lucotavio.restspringboot.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}