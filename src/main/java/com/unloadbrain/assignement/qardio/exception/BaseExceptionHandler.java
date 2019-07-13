package com.unloadbrain.assignement.qardio.exception;


import lombok.Data;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BaseExceptionHandler provides -
 * 1. Handles any unknown error/exception.
 * 2. ErrorResponse, DTO containing error information.
 */
public abstract class BaseExceptionHandler {

    private final Logger log;

    protected BaseExceptionHandler(final Logger log) {
        this.log = log;
    }

    /**
     * Catch any unknown error/exception and provide readable response to end-user.
     *
     * @param ex Throwable object for error/exception.
     * @return the error response DTO {@link ErrorResponse}
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorResponse handleThrowable(final Throwable ex) {
        log.error("Unexpected error", ex);
        return new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected internal server error occurred");
    }

    /**
     * DTO containing error information.
     */
    @Data
    public static class ErrorResponse {

        private final String code;
        private final String message;
    }

}
