package br.com.lucotavio.restspringboot.controller;

import br.com.lucotavio.restspringboot.dto.PersonDto;
import br.com.lucotavio.restspringboot.model.Person;
import br.com.lucotavio.restspringboot.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

    private final PersonService personService;
    private final ModelMapper mapper;
    private final TypeMap<Person, PersonDto> propertyMapper;

    public PersonController(PersonService personService) {
        this.personService = personService;
        this.mapper = new ModelMapper();
        this.propertyMapper = mapper.createTypeMap(Person.class, PersonDto.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all People", description = "Find all People", tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                        @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = PersonDto.class))
                        )
                    }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public List<PersonDto> findAll(){
        List<Person> personList = personService.findAll();
        List<PersonDto> personDtoList;
        personDtoList = personList.stream()
                .map(person -> converterPersonToPersonDto(person, PersonDto.class))
                .map(personDto -> personDto.add(linkTo(methodOn(PersonController.class).findAll()).withSelfRel()))
                .toList();
        return personDtoList;
    }


    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a person using field id", description = "Find a person using field id", tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public PersonDto findById(@PathVariable Long id){
        Person person = personService.findById(id);

        PersonDto personDto = converterPersonToPersonDto(person, PersonDto.class);
        personDto = personDto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personDto;
    }


    @GetMapping(path = "/firstname/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find persons for the firstname where the word can be in anywhere", description = "Find persons for the firstname where the word can be in anywhere", tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDto.class))
                            )
                    }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public List<PersonDto> findByFirstName(@PathVariable String firstName){
        List<Person> personList = personService.findByFirstName(firstName);
        List<PersonDto> personDtoList;
        personDtoList = personList.stream()
                .map(person -> converterPersonToPersonDto(person, PersonDto.class))
                .map(personDto -> personDto.add(linkTo(methodOn(PersonController.class).findByFirstName(firstName)).withSelfRel()))
                .toList();

        return personDtoList;
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new Person", description = "Add a new Person", tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })


    public PersonDto save(@RequestBody PersonDto personDto){
        Person person = convertPersonDtoToPerson(personDto, Person.class);
        person = personService.save(person);

        personDto = converterPersonToPersonDto(person, PersonDto.class);
        personDto = personDto.add(linkTo(methodOn(PersonController.class).save(personDto)).withSelfRel());
        return personDto;
    }


    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates a Person", description = "Updates a  Person", tags = {"People"}, responses = {
            @ApiResponse(description = "Updated", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public PersonDto update(@PathVariable Long id, @RequestBody PersonDto personDto){
        Person person = mapper.map(personDto, Person.class);
        person = personService.update(id, person);

        personDto = converterPersonToPersonDto(person, PersonDto.class);
        personDto = personDto.add(linkTo(methodOn(PersonController.class).update(id, personDto)).withSelfRel());
        return personDto;
    }


    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deletes a Person using field id", description = "Deletes a Person using field id", tags = {"People"}, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
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
}
