package com.joaolucas.dramaJJ.exceptions.handler;

import com.joaolucas.dramaJJ.exceptions.ConflictException;
import com.joaolucas.dramaJJ.exceptions.ExceptionResponse;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception exception){

        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                new Date()
        );
        return ResponseEntity.status(response.errorCode()).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException exception){

        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                new Date()
        );


        return ResponseEntity.status(response.errorCode()).body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionResponse> handleConflictException(ConflictException exception){

        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.CONFLICT.name(),
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                new Date()
        );

        return ResponseEntity.status(response.errorCode()).body(response);
    }

}
