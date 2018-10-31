package com.ideas2it.dvdstore.log;

import org.apache.log4j.Logger;

/**
 * <p>
 * Logs the error messages based on their severity in a file, so that it can be 
 * viewed when required 
 * </p>
 *
 * @author Pavithra S
 */
public class DVDStoreLogger {
    private static Logger logger = Logger.getLogger
        (DVDStoreLogger.class.getName());
    
    public void error(String message) {
        logger.error(message);
    }
    
    public void error(String message, Throwable cause) {
        logger.error(message,cause);
    }
    
    public void warn(String message) {
        logger.warn(message);
    }    
    
    public void debug(String message) {
        logger.debug(message);
    }
    
    public void info(String message) {
        logger.info(message);
    }
    
    public void fatal(String message) {
        logger.fatal(message);
    }
}    

