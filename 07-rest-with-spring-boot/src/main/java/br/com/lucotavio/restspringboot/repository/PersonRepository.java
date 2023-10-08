package br.com.lucotavio.restspringboot.repository;

import br.com.lucotavio.restspringboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}