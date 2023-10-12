package br.com.lucotavio.restspringboot.repository;

import br.com.lucotavio.restspringboot.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
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
                .gender("male")
                .build();


        Person person2 = Person.builder()
                .firstName("Lucas")
                .lastName("Zanon")
                .address("São Paulo-SP")
                .gender("male")
                .build();

        personRepository.save(person1);
        personRepository.save(person2);

        List<Person> personList = personRepository.findByFirstNameContainsIgnoreCase("uc");
        assertEquals(2, personList.size());
    }
}