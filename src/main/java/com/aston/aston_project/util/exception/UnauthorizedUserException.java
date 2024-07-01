package com.aston.aston_project.util.exception;

public class UnauthorizedUserException extends RuntimeException{

    public UnauthorizedUserException(String massage){
        super(massage);
    }
}
