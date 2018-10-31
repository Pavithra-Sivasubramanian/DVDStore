package com.ideas2it.dvdstore.service;

import java.util.Set;

import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.exception.DVDException;

/**
 * <p>
 * The CustomerServiceImpl is used to perform manipulations, if required.
 * </p>
 *
 * @author  Pavithra S 
 */
public interface CustomerService {
    
    /**
     * <p>
     * Used to add new Customer using the inputs obtained from the user
     * </p>
     *
     * @param  customer  The new Customer details to be inserted
     * @return Returns true, if inserted successfully. Otherwise, returns false
     */  
    Boolean insertCustomer(Customer customer) throws DVDException;
    
    /**
     * <p>
     * Used to retrieve the details of a specific Customer based on Customer ID.
     * </p>
     *
     * @param  customerId  The ID of the Customer for which the details is to be 
                           obtained
     * @return Returns details of the Customer for the specified Customer ID. 
     *         Otherwise, returns an empty Category object.
     */
    Customer retrieveById(Integer id) throws DVDException;

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
    Customer retrieveByEmailAndPassword(String name, String password, 
            Boolean status) throws DVDException;
    
    /**
     * <p>
     * Used to make changes in the Customer details for the ID specified by 
     * the user
     * </p>
     * 
     * @param   customer    The altered Customer details
     * @return  Returns true, if updated successfully. Otherwise, returns false. 
     */  
    Boolean updateCustomer(Customer customer) throws DVDException;
    
    /**
     * <p>
     * Used to delete the details of a specific Customer based on ID
     * </p>
     * 
     * @param  customerId  ID of the Customer
     * @return Returns true, if deleted successfully. Otherwise, returns false.      
     */
    Boolean deleteCustomer(Integer id) throws DVDException;
      
    /**
     * <p>
     * Used to display the details of all the Customers
     * </p>
     *
     * @param  status    Status of the Customer checking whether it has been 
     *                   deleted or not
     * @return Returns the details of all the Customers. Otherwise, 
     *         returns an empty object.
     */
    Set<Customer> getCustomers(Boolean status) throws DVDException;
    
    /**
     * <p>
     * Used to get all the DVDs available
     * </p>
     *
     * @param  status    Status of the DVD checking whether it has been 
     *                   deleted or not
     * @return Returns the details of all the DVDs avalaible. Otherwise, 
     *         returns an empty object.
     */
    Set<DVD> getDvds(Boolean status) throws DVDException;
}
