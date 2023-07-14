package com.joaolucas.dramaJJ.exceptions.handler;

import com.joaolucas.dramaJJ.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception exception, WebRequest request){

        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(new Date());
        response.setError(HttpStatus.INTERNAL_SERVER_ERROR.name());
        response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(exception.getMessage());

        return ResponseEntity.status(response.getErrorCode()).body(response);
    }
}
