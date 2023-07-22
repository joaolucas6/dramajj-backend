package com.joaolucas.dramaJJ.exceptions;


import lombok.NoArgsConstructor;

import java.util.Date;

public record ExceptionResponse(String error, Integer errorCode, String message, Date timestamp) {

}
