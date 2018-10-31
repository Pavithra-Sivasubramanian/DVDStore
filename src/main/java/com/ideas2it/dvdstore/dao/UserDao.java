package com.ideas2it.dvdstore.dao;

import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.model.User;

/**
 * <p>
 * The UserController gets the inputs from the user on which the operations 
 * such as login and logout are performed.
 * </p>
 */
public interface UserDao {
    
    /**
     * <p>
     * Used to retrieve the details of a specific User based on User Name 
     * and Password.
     * </p>
     *
     * @param  email     Email ID of the User
     * @param  password  User's password
     * @param  status    Status of the User checking whether it has been 
     *                   deleted or not
     * @return Returns User for the specified User Name and Password. 
     *         Otherwise, returns an empty User object.
     */
    User retrieveByEmailAndPassword(String email, String password,  
            Boolean status) throws DVDException;
}
