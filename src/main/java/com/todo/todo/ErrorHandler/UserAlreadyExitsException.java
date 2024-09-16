package com.todo.todo.ErrorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExitsException extends RuntimeException {
       public UserAlreadyExitsException(String message){
              super(message);
       }
}
