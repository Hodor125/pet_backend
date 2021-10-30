package com.hodor.exception;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/4
 * @description ：
 * @version: 1.0
 */
public class PetBackendException extends RuntimeException {
    public PetBackendException() {
    }

    public PetBackendException(String message) {
        super(message);
    }

    public PetBackendException(String message, Throwable cause) {
        super(message, cause);
    }
}
