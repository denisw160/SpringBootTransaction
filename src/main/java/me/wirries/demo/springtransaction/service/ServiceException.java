package me.wirries.demo.springtransaction.service;

/**
 * An exception with a service.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
public class ServiceException extends Exception {

    /**
     * Constructor.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
