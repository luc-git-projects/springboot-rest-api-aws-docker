package br.com.lucotavio.restspringboot.service;

import br.com.lucotavio.restspringboot.model.Gender;
import br.com.lucotavio.restspringboot.model.Person;
import br.com.lucotavio.restspringboot.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void findAll() {

        Person personSaved1 = Person.builder()
                .id(10L)
                .firstName("Luciano")
                .lastName("Oliveira")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();


        Person personSaved2 = Person.builder()
                .id(10L)
                .firstName("Fernanda")
                .lastName("Zanon")
                .address("São Paulo-SP")
                .gender(Gender.FEMALE)
                .build();

        List<Person> personListSaved = Arrays.asList(personSaved1, personSaved2);
        List<Person> personListExpected = personListSaved.stream()
                .map(person -> person.clone()).toList();

        given(personRepository.findAll()).willReturn(personListSaved);

        List<Person> personListReturned = personService.findAll();

        assertEquals(2, personListReturned.size());
        assertEquals(personListExpected, personListReturned);
    }

    @Test
    void findById() {

        Person personSaved = Person.builder()
                .id(10L)
                .firstName("Luciano")
                .lastName("Oliveira")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        Person personExpected = personSaved.clone();

        Optional<Person> optionalPerson = Optional.of(personSaved);

        given(personRepository.findById(10L)).willReturn(optionalPerson);

        Person personReturned = personService.findById(10L);

        assertEquals(personExpected, personReturned);
    }

    @Test
    void findByFirstName() {

        Person personSaved1 = Person.builder()
                .id(10L)
                .firstName("Luciano")
                .lastName("Oliveira")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();


        Person personSaved2 = Person.builder()
                .id(10L)
                .firstName("Luciamar")
                .lastName("Bernis")
                .address("São Paulo-SP")
                .gender(Gender.MALE)
                .build();

        Person personSaved3 = Person.builder()
                .id(10L)
                .firstName("Lucas")
                .lastName("Arnaldo")
                .address("Contagem-MG")
                .gender(Gender.MALE)
                .build();

        List<Person> personListSaved = Arrays.asList(personSaved1, personSaved2, personSaved3);
        List<Person> personListExpected = personListSaved.stream()
                .map(person -> person.clone()).toList();

        given(personRepository.findByFirstNameContainsIgnoreCase("Luc")).willReturn(personListSaved);

        List<Person> personListReturned = personService.findByFirstName("Luc");

        assertEquals(3, personListReturned.size());
        assertEquals(personListExpected, personListReturned);
    }

    @Test
    void save() {

        Person personBeforeSaved = Person.builder()
                .firstName("Luciano")
                .lastName("Oliveira")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        Person personAfterSaved = personBeforeSaved.clone();
        personAfterSaved.setId(34L);

        Person personExpected = personAfterSaved.clone();

        given(personRepository.save(personBeforeSaved)).willReturn(personAfterSaved);

        Person personReturned = personService.save(personBeforeSaved);
        assertEquals(personExpected, personReturned);
    }

    @Test
    void update() {

        Person personBeforeSaved = Person.builder()
                .firstName("Luciano")
                .lastName("Oliveira")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        Person personAfterSaved = personBeforeSaved.clone();
        personAfterSaved.setId(34L);

        Person personExpected = personAfterSaved.clone();

        Optional<Person> optionalPerson = Optional.of(personAfterSaved);

        given(personRepository.findById(34L)).willReturn(optionalPerson);
        given(personRepository.save(personBeforeSaved)).willReturn(personAfterSaved);

        Person personReturned = personService.update(34L, personBeforeSaved);
        assertEquals(personExpected, personReturned);
    }

    @Test
    void delete() {

        Person personAfterSaved = Person.builder()
                .id(34L)
                .firstName("Luciano")
                .lastName("Oliveira")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        Optional<Person> optionalPerson = Optional.of(personAfterSaved);

        given(personRepository.findById(34L)).willReturn(optionalPerson);
        personService.delete(34L);
    }
}