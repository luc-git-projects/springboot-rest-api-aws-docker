package br.com.lucotavio.restspringboot.service;

import br.com.lucotavio.restspringboot.exception.EntityNotFoundException;
import br.com.lucotavio.restspringboot.model.Book;
import br.com.lucotavio.restspringboot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll(){
        log.info("Find All Books");
        List<Book> bookList = bookRepository.findAll();
        return bookList;
    }

    public Book findById(Long id){
        log.info("Finding book by id");
        String messageException = STR."Book with id = \{id} not found";
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageException));

        return book;
    }

    public Book save(Book book){
        log.info("Save a book");
        book = bookRepository.save(book);
        return book;
    }

    public Book update(Long id, Book book){
        log.info("Update a book");
        findById(id);
        book.setId(id);
        book = bookRepository.save(book);
        return book;
    }

    public void deleteById(Long id){
        log.info("Deleting a person by id");
        findById(id);
        bookRepository.deleteById(id);
    }
}
