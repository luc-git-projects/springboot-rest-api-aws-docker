package br.com.lucotavio.restspringboot.dto;

import br.com.lucotavio.restspringboot.controller.BookController;
import br.com.lucotavio.restspringboot.model.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import static br.com.lucotavio.restspringboot.util.DataConverter.localDateToString_dd_mm_yyyy_withSlash;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
@NoArgsConstructor
public class BookDto extends RepresentationModel<BookDto> implements Serializable {

    private Long key;
    private String title;
    private String author;
    private String price ;
    private String launchDate;


    public BookDto(Book book) {
        key = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
        price = book.getPrice().toString();
        price = price.replace(".", ",");
        launchDate = localDateToString_dd_mm_yyyy_withSlash(book.getLaunchDate());
    }

    private BookDto createHateoas(Long id, BookDto bookDto){
        if(id == null){
            return this.add(linkTo(methodOn(BookController.class).findAll()).withSelfRel());
        }else{
            return this.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        }
    }

    private BookDto createHateoas(BookDto bookDto){
        return createHateoas(null, bookDto);
    }
}