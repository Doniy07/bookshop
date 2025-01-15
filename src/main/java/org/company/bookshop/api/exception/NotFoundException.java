package org.company.bookshop.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private final int status;

    public NotFoundException(String message, int status) {
        super(message);
        this.status = status;
    }
    public NotFoundException(String message) {
        super(message);
        this.status = 404;
    }
}