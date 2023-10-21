package br.com.lucotavio.restspringboot.repository;

import br.com.lucotavio.restspringboot.model.Gender;
import br.com.lucotavio.restspringboot.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;


    @Test
    void findByFirstNameContainsIgnoreCase() {


        Person person1 = Person.builder()
                .firstName("Luciano")
                .lastName("Oliveira")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();


        Person person2 = Person.builder()
                .firstName("Lucas")
                .lastName("Zanon")
                .address("São Paulo-SP")
                .gender(Gender.MALE)
                .build();

        personRepository.save(person1);
        personRepository.save(person2);

        List<Person> personList = personRepository.findByFirstNameContainsIgnoreCase("uc");
        assertEquals(2, personList.size());
    }
}