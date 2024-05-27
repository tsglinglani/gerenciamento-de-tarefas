package com.tsg.cbyk.gerenciamentodetarefas.config.erros;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerAPI {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErroDetalhe> handleRuntimeException(EntityExistsException ex, WebRequest request) {
        var erro = new ErroDetalhe(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(erro, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroDetalhe> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        var erro = new ErroDetalhe(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErroDetalhe> handleRuntimeException(NullPointerException ex, WebRequest request) {
        var erro = new ErroDetalhe(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }
}
