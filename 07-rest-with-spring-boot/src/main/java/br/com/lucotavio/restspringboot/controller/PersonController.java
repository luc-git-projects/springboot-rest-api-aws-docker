package br.com.lucotavio.restspringboot.controller;

import br.com.lucotavio.restspringboot.dto.PersonDto;
import br.com.lucotavio.restspringboot.model.Person;
import br.com.lucotavio.restspringboot.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "person")
public class PersonController {

    private final PersonService personService;

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto findById(@PathVariable Long id){
        Person person = personService.findById(id);
        return new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getAddress(), person.getGender());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDto> findAll(){
        List<Person> personList = personService.findAll();
        List<PersonDto> personDtoList;
        personDtoList = personList.stream()
                .map(p -> new PersonDto(p.getId(), p.getFirstName(), p.getLastName(), p.getAddress(), p.getGender()))
                .toList();
        return personDtoList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto save(@RequestBody Person person){
        person = personService.save(person);
        return new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getAddress(), person.getGender());
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto update(@PathVariable Long id, @RequestBody Person person){
        person = personService.update(id, person);
        return new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getAddress(), person.getGender());
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        personService.delete(id);
    }
}
