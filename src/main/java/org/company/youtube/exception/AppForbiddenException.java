package org.company.youtube.exception;

public class AppForbiddenException extends RuntimeException{
    public AppForbiddenException(String message){
        super(message);
    }
}
