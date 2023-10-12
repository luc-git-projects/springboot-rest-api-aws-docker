package br.com.lucotavio.restspringboot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto extends RepresentationModel<PersonDto> implements Serializable {

    private Long key;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private  String lastName;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    @NotEmpty
    private String gender;
}