package com.unloadbrain.assignement.qardio.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Handle application specific exception in user friendly way.
 */
@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends BaseExceptionHandler {

    public ApplicationExceptionHandler() {
        super(log);
    }

}
