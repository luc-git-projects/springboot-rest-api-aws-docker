package br.com.lucotavio.restspringboot.exception.handler;

import br.com.lucotavio.restspringboot.exception.ExceptionResponse;
import br.com.lucotavio.restspringboot.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlePersonNotfoundException(Exception ex, WebRequest request){

        ExceptionResponse exceptionResponse  = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
