package br.com.lucotavio.restspringboot.dto;

import br.com.lucotavio.restspringboot.model.Author;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;
import static br.com.lucotavio.restspringboot.util.DataConverter.localDateToString_dd_mm_yyyy_withSlash;

/**
 * DTO for {@link br.com.lucotavio.restspringboot.model.Author}
 */
@Getter
@Setter
public class AuthorDto implements Serializable {

    private Long key;

    @NotNull(message = "First name name can not be null")
    @NotEmpty(message = "First name name can not be empty")
    private String name;

    private String country;

    private String birthDate;

    private List<BookDto> bookDtoList;

    public AuthorDto(Author author) {
        key = author.getId();
        name = author.getName();
        country = author.getCountry();
        birthDate = localDateToString_dd_mm_yyyy_withSlash(author.getBirthDate());
        bookDtoList = returnBooks(author);
    }

    private List<BookDto> returnBooks(Author author){
        List<BookDto> bookList;
        bookList = author.getAuthorHasBooks()
                .stream()
                .map(authorHasBook-> authorHasBook.getBook())
                .map(book -> new BookDto(book))
                .toList();

        return bookList;
    }


}