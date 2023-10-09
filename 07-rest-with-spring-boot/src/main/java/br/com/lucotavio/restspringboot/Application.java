package br.com.lucotavio.restspringboot;

import br.com.lucotavio.restspringboot.converter.YamlJackson2TttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
