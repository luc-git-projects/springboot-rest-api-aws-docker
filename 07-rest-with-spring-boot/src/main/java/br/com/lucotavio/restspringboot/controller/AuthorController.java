package br.com.lucotavio.restspringboot.controller;

import br.com.lucotavio.restspringboot.dto.AuthorDto;
import br.com.lucotavio.restspringboot.model.Author;
import br.com.lucotavio.restspringboot.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/authors/v1")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/{id}")
    public AuthorDto findById(@PathVariable Long id){
        Author author = authorService.findById(id);
        AuthorDto authorDto = new AuthorDto(author);
        return authorDto;
    }
}
