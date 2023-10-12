package br.com.lucotavio.restspringboot.repository;

import br.com.lucotavio.restspringboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByFirstNameContainsIgnoreCase(String firstName);
}