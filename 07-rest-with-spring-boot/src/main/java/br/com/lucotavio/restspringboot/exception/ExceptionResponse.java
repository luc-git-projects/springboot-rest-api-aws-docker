package br.com.lucotavio.restspringboot.exception;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse implements Serializable {

    private LocalDateTime timestamp;
    private String message;
    private String details;
}
