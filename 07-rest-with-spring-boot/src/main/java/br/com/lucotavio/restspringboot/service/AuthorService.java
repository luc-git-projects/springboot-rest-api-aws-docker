package br.com.lucotavio.restspringboot.service;

import br.com.lucotavio.restspringboot.exception.EntityNotFoundException;
import br.com.lucotavio.restspringboot.model.Author;
import br.com.lucotavio.restspringboot.model.AuthorHasBook;
import br.com.lucotavio.restspringboot.model.Book;
import br.com.lucotavio.restspringboot.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author findById(Long id){
        log.info("Find Author by id");
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(STR."Author with id = \{id} not found"));

        return author;
    }
}
