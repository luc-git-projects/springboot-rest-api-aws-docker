package br.com.lucotavio.restspringboot.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BookDto extends RepresentationModel<BookDto> implements Serializable {

    private Long key;
    private String title;
    private String Author;
    private LocalDate launchDate;
    private BigDecimal price ;
}