package br.com.lucotavio.restspringboot.dto;

import br.com.lucotavio.restspringboot.model.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link br.com.lucotavio.restspringboot.model.Book}
 */
@Getter
@Setter
public class BookDto implements Serializable {

    private Long key;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private List<String> authors;

    @NotNull
    private LocalDate launchDate;

    @NotNull
    private BigDecimal price;

    @NotNull
    @NotEmpty
    private String isbn;

    public BookDto(Book book){
        key = book.getId();
        title = book.getTitle();
        authors = returnAuthors(book);
        launchDate = book.getLaunchDate();
        price = book.getPrice();
        isbn = book.getIsbn();

    }

    private List<String> returnAuthors(Book book){
        List<String> authors;
        authors = book.getAuthorHasBooks().stream()
                .map(authorHasBook -> authorHasBook.getAuthor().getName())
                .toList();
        return authors;
    }
}