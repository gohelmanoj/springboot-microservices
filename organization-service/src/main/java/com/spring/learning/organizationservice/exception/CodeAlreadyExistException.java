package com.spring.learning.organizationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CodeAlreadyExistException extends RuntimeException{

    private String code;

    public CodeAlreadyExistException(String code) {
        super(String.format("%s code is already exist ", code));
    }
}
