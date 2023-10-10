package br.com.lucotavio.restspringboot.controller;

import br.com.lucotavio.restspringboot.configuration.WebConfig;
import br.com.lucotavio.restspringboot.dto.PersonDto;
import br.com.lucotavio.restspringboot.model.Person;
import br.com.lucotavio.restspringboot.service.PersonService;
import jakarta.validation.Valid;
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

    public PersonController(PersonService personService, ModelMapper mapper) {
        this.personService = personService;
        this.mapper = mapper;
        this.propertyMapper = this.mapper.createTypeMap(Person.class, PersonDto.class);
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public PersonDto findById(@PathVariable Long id){
        Person person = personService.findById(id);

        mapIdToKey();
        PersonDto personDto = convertPersonToPersonDto(person, PersonDto.class);

        personDto = createHateoas(id, personDto);
        return personDto;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDto> findAll(){
        List<Person> personList = personService.findAll();

        mapIdToKey();

        List<PersonDto> personDtoList;
        personDtoList = personList.stream()
                .map(person -> convertPersonToPersonDto(person, PersonDto.class))
                .map(personDto -> createHateoas(personDto))
                .toList();

        return personDtoList;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto save(@Valid @RequestBody PersonDto personDto){
        Person person = mapper.map(personDto, Person.class);
        person = personService.save(person);

        mapIdToKey();
        personDto = convertPersonToPersonDto(person, PersonDto.class);

        personDto = createHateoas(person.getId(), personDto);
        return personDto;
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, WebConfig.APPLICATION_YML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public PersonDto update(@PathVariable Long id, @RequestBody PersonDto personDto){
        Person person = mapper.map(personDto, Person.class);
        person = personService.update(id, person);

        mapIdToKey();
        personDto = convertPersonToPersonDto(person, PersonDto.class);

        personDto = createHateoas(person.getId(), personDto);
        return personDto;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        personService.delete(id);
    }

    private void mapIdToKey(){
        propertyMapper.addMapping(Person::getId, PersonDto::setKey);
    }

    private PersonDto convertPersonToPersonDto(Person person, Class<PersonDto> clazz){
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
