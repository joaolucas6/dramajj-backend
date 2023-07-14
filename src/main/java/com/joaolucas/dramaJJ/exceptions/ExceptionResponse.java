package com.joaolucas.dramaJJ.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExceptionResponse {

    private String error;
    private Integer errorCode;
    private String message;
    private Date timestamp;

}
