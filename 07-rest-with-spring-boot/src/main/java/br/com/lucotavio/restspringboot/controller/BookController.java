package br.com.lucotavio.restspringboot.controller;

import br.com.lucotavio.restspringboot.dto.BookDto;
import br.com.lucotavio.restspringboot.model.Book;
import br.com.lucotavio.restspringboot.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books/v1")
@Tag(name = "Book", description = "Endpoints for Managing Books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all Books", description = "Find all Books", tags = {"Book"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class))
                            )
                    }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public List<BookDto> findAll(){
        List<Book> bookList = bookService.findAll();
        List<BookDto> bookDtoList;

        bookDtoList = bookList.stream()
                .map(book -> new BookDto(book))
                .map(bookDto -> bookDto.add(linkTo(methodOn(BookController.class).findAll()).withSelfRel()))
                .toList();

        return bookDtoList;
    }


    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a book using field id", description = "Find a book using field id", tags = {"Book"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public BookDto findById(@PathVariable(name = "id") Long id){
        Book book = bookService.findById(id);
        BookDto bookDto = new BookDto(book);
        bookDto = bookDto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return bookDto;
    }


    @GetMapping(path = "/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a book using field title", description = "Find a book using field title", tags = {"Book"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class))
                            )
                    }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public List<BookDto> findByTitle(@PathVariable(name = "title") String title){
        List<Book> bookList = bookService.findByTitle(title);
        List<BookDto> bookDtoList;

        bookDtoList = bookList.stream()
                .map(book -> new BookDto(book))
                .map(bookDto -> bookDto.add(linkTo(methodOn(BookController.class).findByTitle(title)).withSelfRel()))
                .toList();

        return bookDtoList;
    }


    @GetMapping(path = "/author/{author}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a book using field author", description = "Find a book using field author", tags = {"Book"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class))
                            )
                    }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public List<Book> findByAuthor(@PathVariable(name = "author") String author){
        List<Book> bookList = bookService.findByAuthor(author);
        List<BookDto> bookDtoList;

        bookDtoList = bookList.stream()
                .map(book -> new BookDto(book))
                .map(bookDto -> bookDto.add(linkTo(methodOn(BookController.class).findByAuthor(author)).withSelfRel()))
                .toList();

        return bookList;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new Book", description = "Add a new Book", tags = {"Book"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public BookDto save(@RequestBody BookDto bookDto){
        Book book = new Book(bookDto);
        book = bookService.save(book);
        bookDto = new BookDto(book);
        bookDto = bookDto.add(linkTo(methodOn(BookController.class).save(bookDto)).withSelfRel());
        return bookDto;
    }


    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates a Book", description = "Updates a  Book", tags = {"Book"}, responses = {
            @ApiResponse(description = "Updated", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public BookDto update(@PathVariable(name = "id") Long id, @RequestBody BookDto bookDto){
        Book book = book = new Book(bookDto);
        book = bookService.update(id, book);
        bookDto = new BookDto(book);
        bookDto = bookDto.add(linkTo(methodOn(BookController.class).update(id, bookDto)).withSelfRel());
        return bookDto;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deletes a Book using field id", description = "Deletes a Book using field id", tags = {"Book"}, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public void delete(@PathVariable(name = "id") Long id){
        bookService.deleteById(id);
    }

    private BookDto createHateoas(Long id, BookDto bookDto){
        if(id == null){
            return bookDto.add(linkTo(methodOn(BookController.class).findAll()).withSelfRel());
        }else{
            return bookDto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        }
    }

    private BookDto createHateoas(BookDto bookDto){
        return createHateoas(null, bookDto);
    }

}
