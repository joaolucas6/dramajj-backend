package com.joaolucas.dramaJJ.exceptions;


import java.time.LocalDateTime;
import java.util.Date;

public record ExceptionResponse(String error, Integer errorCode, String message, LocalDateTime timestamp) {

}
