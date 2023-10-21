package br.com.lucotavio.restspringboot.exception.handler;

import br.com.lucotavio.restspringboot.exception.ExceptionResponse;
import br.com.lucotavio.restspringboot.exception.EntityNotFoundException;
import br.com.lucotavio.restspringboot.exception.ValidationErrorResponse;
import br.com.lucotavio.restspringboot.exception.Violation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlePersonNotfoundException(EntityNotFoundException ex, WebRequest request){

        ExceptionResponse exceptionResponse  = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ValidationErrorResponse error  = new ValidationErrorResponse();

        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            Violation violation = new Violation(fieldError.getField(), fieldError.getDefaultMessage());
            error.getViolations().add(violation);
        }
        return ResponseEntity.badRequest().body(error);
    }
}
