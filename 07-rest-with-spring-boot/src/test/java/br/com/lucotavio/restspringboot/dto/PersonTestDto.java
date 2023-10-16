package br.com.lucotavio.restspringboot.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PersonTestDto implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;

}
