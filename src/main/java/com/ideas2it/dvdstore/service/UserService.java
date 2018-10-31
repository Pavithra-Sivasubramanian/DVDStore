package com.ideas2it.dvdstore.service;

import java.util.Set;

import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.User;

/**
 * <p>
 * The UserService is used to perform manipulations, if required.
 * </p>
 *
 * @author  Pavithra S 
 */
public interface UserService {

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
    
    /**
     * <p>
     * Used to display the details of all the DVD's in the List
     * </p>
     *
     * @param  status   Status of the DVD checking whether it has been 
     *                  deleted or not
     * @return Returns the details of all the DVDs. Otherwise, 
     *         returns an empty DVD object.
     */        
    Set<DVD> getDvds(Boolean status) throws DVDException; 
    
    /**
     * <p>
     * Used to retrieve the details of a specific Customer based on Customer 
     * Name and Password.
     * </p>
     *
     * @param  email     Email ID of the Customer
     * @param  password  Customer's password
     * @param  status    Status of the Customer checking whether it has been 
     *                   deleted or not
     * @return Returns Customer for the specified Customer Name and Password. 
     *         Otherwise, returns an empty Customer object.
     */
    Customer retrieveCustomer(String name, String password, 
        Boolean status) throws DVDException;
            
    /**
     * <p>
     * Used to add new Customer using the inputs obtained from the user
     * </p>
     *
     * @param  customer  The new Customer details to be inserted
     * @return Returns true, if inserted successfully. Otherwise, returns false
     */  
    Boolean insertCustomer(Customer customer) throws DVDException;
}            

