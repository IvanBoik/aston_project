package com.aston.aston_project.util.exception;

public class PrescriptionRequiredException extends UnauthorizedUserException{

    public PrescriptionRequiredException(String massage) {
        super(massage);
    }
}
