package com.ideas2it.dvdstore.exception;

/**
 * <p>
 * Represents the Exception defined by the User.
 * </p>
 *
 * @author Pavithra S
 */
public class DVDException extends Exception {
    public DVDException(String message, Throwable cause) {
        super(message,cause);
    }
    
    public DVDException(String message) {
        super(message);
    }
}

