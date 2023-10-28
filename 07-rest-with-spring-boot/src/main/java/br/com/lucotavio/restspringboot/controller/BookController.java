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
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/books/v1")
@Tag(name = "Book", description = "Endpoints for Managing Books")
public class BookController {

    private final BookService bookService;
    private final ModelMapper mapper;
    private final TypeMap<Book, BookDto> propertyMapper;

    public BookController(BookService bookService) {
        this.bookService = bookService;
        this.mapper = new ModelMapper();
        this.propertyMapper = mapper.createTypeMap(Book.class, BookDto.class);
    }


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
                .map(book -> converterBookToBookDto(book, BookDto.class))
                .map(bookDto -> createHateoas(bookDto))
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
        BookDto bookDto = converterBookToBookDto(book, BookDto.class);
        bookDto = createHateoas(id, bookDto);
        return bookDto;
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
        Book book = convertBookDtoToBook(bookDto, Book.class);
        book = bookService.save(book);
        bookDto = converterBookToBookDto(book, BookDto.class);
        bookDto = createHateoas(bookDto);
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
        Book book = convertBookDtoToBook(bookDto, Book.class);
        book = bookService.update(id, book);
        bookDto = converterBookToBookDto(book, BookDto.class);
        bookDto = createHateoas(id, bookDto);
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

    private BookDto converterBookToBookDto(Book book, Class<BookDto> clazz){
        propertyMapper.addMapping(Book::getId, BookDto::setKey);
        return mapper.map(book, clazz);
    }

    private Book convertBookDtoToBook(BookDto bookDto, Class<Book> clazz){
        return mapper.map(bookDto, clazz);
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
