package br.com.lucotavio.restspringboot.controller;

import br.com.lucotavio.restspringboot.configuration.WebConfig;
import br.com.lucotavio.restspringboot.dto.PersonDto;
import br.com.lucotavio.restspringboot.model.Person;
import br.com.lucotavio.restspringboot.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/persons/v1")
public class PersonController {

    private final PersonService personService;
    private final ModelMapper mapper;


    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public PersonDto findById(@PathVariable Long id){
        Person person = personService.findById(id);
        return mapper.map(person, PersonDto.class);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDto> findAll(){
        List<Person> personList = personService.findAll();
        List<PersonDto> personDtoList;
        personDtoList = personList.stream()
                .map(p -> mapper.map(p, PersonDto.class))
                .toList();
        return personDtoList;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto save(@Valid @RequestBody PersonDto personDto){
        Person person = mapper.map(personDto, Person.class);
        person = personService.save(person);
        return mapper.map(person, PersonDto.class);
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public PersonDto update(@PathVariable Long id, @RequestBody PersonDto personDto){
        Person person = mapper.map(personDto, Person.class);
        person = personService.update(id, person);
        return mapper.map(person, PersonDto.class);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        personService.delete(id);
    }
}
