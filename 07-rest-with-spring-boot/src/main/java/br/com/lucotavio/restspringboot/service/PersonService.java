package br.com.lucotavio.restspringboot.service;

import br.com.lucotavio.restspringboot.exception.PersonNotFoundException;
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

    public Person findById(Long id){
        log.info("Finding one person");
        String message = STR."Person with ID = \{id} not found";
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(message));

        return person;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person save(Person person) {
        log.info("Save person");
        return personRepository.save(person);
    }

    public Person update(Long id, Person person) {
        log.info("Update person");
        findById(id);
        person.setId(id);
        return personRepository.save(person);
    }

    public void delete(Long id){
        log.info("Deleting one person");
        findById(id);
        personRepository.deleteById(id);
    }
}
