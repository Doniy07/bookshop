package org.company.bookshop.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private final int status;

    public BadRequestException(String message, int status) {
        super(message);
        this.status = status;
    }
    public BadRequestException(String message) {
        super(message);
        this.status = 400;
    }
}