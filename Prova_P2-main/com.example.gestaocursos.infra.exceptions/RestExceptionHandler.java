package com.example.gestaocursos.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Esta classe vai "ouvir" todas as exceções lançadas nos controllers
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // Este método será chamado quando uma RuntimeException for lançada
    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
        // Retornamos um ResponseEntity com a mensagem da exceção e o status 409
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
}