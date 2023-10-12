package br.com.lucotavio.restspringboot.controller;

import br.com.lucotavio.restspringboot.dto.PersonDto;
import br.com.lucotavio.restspringboot.model.Person;
import br.com.lucotavio.restspringboot.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/persons/v1")
public class PersonController {

    private final PersonService personService;
    private final ModelMapper mapper;
    private final TypeMap<Person, PersonDto> propertyMapper;

    public PersonController(PersonService personService) {
        this.personService = personService;
        this.mapper = new ModelMapper();
        this.propertyMapper = mapper.createTypeMap(Person.class, PersonDto.class);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto findById(@PathVariable Long id){
        Person person = personService.findById(id);

        PersonDto personDto = converterPersonToPersonDto(person, PersonDto.class);
        personDto = createHateoas(id, personDto);
        return personDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDto> findAll(){
        List<Person> personList = personService.findAll();
        List<PersonDto> personDtoList;
        personDtoList = personList.stream()
                .map(person -> converterPersonToPersonDto(person, PersonDto.class))
                .map(personDto -> createHateoas(personDto))
                .toList();
        return personDtoList;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto save(@RequestBody PersonDto personDto){
        Person person = convertPersonDtoToPerson(personDto, Person.class);
        person = personService.save(person);

        personDto = converterPersonToPersonDto(person, PersonDto.class);
        personDto = createHateoas(person.getId(), personDto);
        return personDto;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto update(@PathVariable Long id, @RequestBody PersonDto personDto){
        Person person = mapper.map(personDto, Person.class);
        person = personService.update(id, person);

        personDto = converterPersonToPersonDto(person, PersonDto.class);
        personDto = createHateoas(id, personDto);
        return personDto;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        personService.delete(id);
    }

    private PersonDto converterPersonToPersonDto(Person person, Class<PersonDto> clazz){
        propertyMapper.addMapping(Person::getId, PersonDto::setKey);
        return mapper.map(person, clazz);
    }

    private Person convertPersonDtoToPerson(PersonDto personDto, Class<Person> clazz){
        return mapper.map(personDto, clazz);
    }

    private PersonDto createHateoas(Long id, PersonDto personDto){
        if(id == null){
            return personDto.add(linkTo(methodOn(PersonController.class).findAll()).withSelfRel());
        }else{
            return personDto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        }
    }

    private PersonDto createHateoas(PersonDto personDto){
        return createHateoas(null, personDto);
    }


}
