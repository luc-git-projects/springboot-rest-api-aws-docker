package br.com.lucotavio.restspringboot.integrationtests.controller.withjson;

import br.com.lucotavio.restspringboot.configs.TestConfigs;
import br.com.lucotavio.restspringboot.dto.PersonTestDto;
import br.com.lucotavio.restspringboot.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonTestDto personDto;

    @BeforeAll
    public static void setupd(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        personDto = new PersonTestDto();
    }
    @Test
    @Order(1)
    public void testCreate(){
        mockPerson();
        var content =
        given()
                .basePath("/swagger-ui/index.html")
                .port(TestConfigs.SERVER_PORT)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                .asString();

        assertTrue(content.contains("Swagger UI"));

    }

    private void mockPerson(){
        personDto = PersonTestDto
                .builder()
                .firstName("Richard")
                .lastName("Stallman")
                .address("New York City, New york, US")
                .build();
    }
}
