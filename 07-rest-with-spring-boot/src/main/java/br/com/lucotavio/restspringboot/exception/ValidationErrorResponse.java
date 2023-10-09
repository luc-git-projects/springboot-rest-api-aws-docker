package br.com.lucotavio.restspringboot.exception;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse implements Serializable {

    private List<Violation> violations = new ArrayList<>();
}
