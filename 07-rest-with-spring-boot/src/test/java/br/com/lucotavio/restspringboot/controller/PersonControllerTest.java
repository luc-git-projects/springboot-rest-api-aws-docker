package br.com.lucotavio.restspringboot.controller;


import br.com.lucotavio.restspringboot.dto.PersonDto;
import br.com.lucotavio.restspringboot.model.Gender;
import br.com.lucotavio.restspringboot.model.Person;
import br.com.lucotavio.restspringboot.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void findById() throws Exception {

        Person person = Person.builder()
                .id(10L)
                .firstName("Luciano")
                .lastName("Oliveira")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        given(personService.findById(10L)).willReturn(person);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/persons/v1/{id}", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("key")
                        .isNotEmpty())
                .andExpect(jsonPath("firstName")
                        .value("Luciano"))
                .andExpect(jsonPath("lastName")
                        .value("Oliveira"))
                .andExpect(jsonPath("address")
                        .value("Belo Horizonte-MG"))
                .andExpect(jsonPath("gender")
                        .value(Gender.MALE.getName().toUpperCase()));
    }

    @Test
    void findAll() throws Exception {

        Person person1 = Person.builder()
                .id(14L)
                .firstName("Luciano")
                .lastName("Oliveira")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        Person person2 = Person.builder()
                .id(22L)
                .firstName("Otavia")
                .lastName("Xavier")
                .address("Rio de Janeiro-RJ")
                .gender(Gender.FEMALE)
                .build();

        given(personService.findAll()).willReturn(Arrays.asList(person1, person2));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/persons/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))

                .andExpect(jsonPath("$[0].key").value(14))
                .andExpect(jsonPath("$[0].firstName").value("Luciano"))
                .andExpect(jsonPath("$[0].lastName").value("Oliveira"))
                .andExpect(jsonPath("$[0].address").value("Belo Horizonte-MG"))
                .andExpect(jsonPath("$[0].gender").value(Gender.MALE.getName().toUpperCase()))

                .andExpect(jsonPath("$[1].key").value(22))
                .andExpect(jsonPath("$[1].firstName").value("Otavia"))
                .andExpect(jsonPath("$[1].lastName").value("Xavier"))
                .andExpect(jsonPath("$[1].address").value("Rio de Janeiro-RJ"))
                .andExpect(jsonPath("$[1].gender").value(Gender.FEMALE.getName().toUpperCase()));

    }

    @Test
    void findByFirstName() throws Exception {

        Person person1 = Person.builder()
                .id(14L)
                .firstName("Luciano")
                .lastName("Oliveira")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        Person person2 = Person.builder()
                .id(22L)
                .firstName("Lucas")
                .lastName("Machado")
                .address("Caxias do Sul-RS")
                .gender(Gender.MALE)
                .build();


        Person person3 = Person.builder()
                .id(26L)
                .firstName("Luana")
                .lastName("Fernandez")
                .address("Salvador-BA")
                .gender(Gender.FEMALE)
                .build();

        given(personService.findByFirstName("lu")).willReturn(Arrays.asList(person1, person2, person3));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/persons/v1/firstname/{firstName}", "lu")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].key").value(14))
                .andExpect(jsonPath("$[0].firstName").value("Luciano"))
                .andExpect(jsonPath("$[0].lastName").value("Oliveira"))
                .andExpect(jsonPath("$[0].address").value("Belo Horizonte-MG"))
                .andExpect(jsonPath("$[0].gender").value(Gender.MALE.getName().toUpperCase()))

                .andExpect(jsonPath("$[1].key").value(22))
                .andExpect(jsonPath("$[1].firstName").value("Lucas"))
                .andExpect(jsonPath("$[1].lastName").value("Machado"))
                .andExpect(jsonPath("$[1].address").value("Caxias do Sul-RS"))
                .andExpect(jsonPath("$[1].gender").value(Gender.MALE.getName().toUpperCase()))

                .andExpect(jsonPath("$[2].key").value(26))
                .andExpect(jsonPath("$[2].firstName").value("Luana"))
                .andExpect(jsonPath("$[2].lastName").value("Fernandez"))
                .andExpect(jsonPath("$[2].address").value("Salvador-BA"))
                .andExpect(jsonPath("$[2].gender").value(Gender.FEMALE.getName().toUpperCase()));


    }


    @Test
    void save() throws Exception {

        Person personBeforeSaved = Person.builder()
                .firstName("Pedro")
                .lastName("Silvestre")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();


        Person personSaved = Person.builder()
                .id(12L)
                .firstName("Pedro")
                .lastName("Silvestre")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        PersonDto personDto = PersonDto.builder()
                .firstName("Pedro")
                .lastName("Silvestre")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        given(personService.save(personBeforeSaved)).willReturn(personSaved);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/persons/v1" )
                .content(asJsonString(personDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("key")
                        .isNotEmpty())
                .andExpect(jsonPath("firstName")
                        .value("Pedro"))
                .andExpect(jsonPath("lastName")
                        .value("Silvestre"))
                .andExpect(jsonPath("address")
                        .value("Belo Horizonte-MG"))
                .andExpect(jsonPath("gender")
                        .value(Gender.MALE.getName().toUpperCase()));
    }


    @Test
    void update() throws Exception {

        Person personBeforeSaved = Person.builder()
                .firstName("Pedro")
                .lastName("Silvestre")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        Person personSaved = Person.builder()
                .id(12L)
                .firstName("Pedro")
                .lastName("Silvestre")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        PersonDto personDto = PersonDto.builder()
                .firstName("Pedro")
                .lastName("Silvestre")
                .address("Belo Horizonte-MG")
                .gender(Gender.MALE)
                .build();

        given(personService.update(12L, personBeforeSaved)).willReturn(personSaved);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/persons/v1/{id}", 12)
                .content(asJsonString(personDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("key")
                        .isNotEmpty())
                .andExpect(jsonPath("firstName")
                        .value("Pedro"))
                .andExpect(jsonPath("lastName")
                        .value("Silvestre"))
                .andExpect(jsonPath("address")
                        .value("Belo Horizonte-MG"))
                .andExpect(jsonPath("gender")
                        .value(Gender.MALE.getName().toUpperCase()));
    }


    @Test
    void delete() throws Exception {

        doNothing().when(personService).delete(isA(Long.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/persons/v1/{id}", isA(Long.class))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}