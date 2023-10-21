package br.com.lucotavio.restspringboot.service;

import br.com.lucotavio.restspringboot.exception.EntityNotFoundException;
import br.com.lucotavio.restspringboot.model.Person;
import br.com.lucotavio.restspringboot.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.lang.StringTemplate.STR;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        log.info("Find All People");
        List<Person> personList = personRepository.findAll();;
        return personList;
    }

    public Person findById(Long id){
        log.info("Finding person by id");
        String message = STR."Person with ID = \{id} not found";
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(message));
        return person;
    }

    public List<Person> findByFirstName(String firstName){
        List<Person> personList = personRepository.findByFirstNameContainsIgnoreCase(firstName);
        return personList;
    }

    public Person save(Person person) {
        log.info("Save person");
        person = personRepository.save(person);
        return person;
    }

    public Person update(Long id, Person person) {
        log.info("Update person");
        findById(id);
        person.setId(id);
        person = personRepository.save(person);
        return person;
    }

    public void delete(Long id){
        log.info("Deleting a person by id");
        findById(id);
        personRepository.deleteById(id);
    }
}
