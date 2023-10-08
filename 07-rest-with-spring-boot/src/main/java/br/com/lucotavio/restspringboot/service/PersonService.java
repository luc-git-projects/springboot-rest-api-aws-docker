package br.com.lucotavio.restspringboot.service;

import br.com.lucotavio.restspringboot.model.Person;
import br.com.lucotavio.restspringboot.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person findById(Long id){
        log.info("Finding one person");
        Person person = new Person();
        person.setId(id);
        person.setFirstName("Luciano");
        person.setLastName("Oliveira");
        person.setAddress("Belo Horizonte - Minas Gerais");
        person.setGender("Male");

        return person;
    }

    public List<Person> findAll() {

        log.info("Finding all persons");
        List<Person> persons = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }

        return  persons;
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId((long) i);
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setAddress("some address in Brazil " + i);

        if(i % 2 == 0){
            person.setGender("Male");
        }else{
            person.setGender("Female");
        }

        return person;
    }

    public Person save(Person person) {
        log.info("Save person");
        return person;
    }

    public Person update(Long id, Person person) {
        log.info("Update person");
        person.setId(id);
        return person;
    }

    public void delete(Long id){
        log.info("Deleting one person");
    }
}
