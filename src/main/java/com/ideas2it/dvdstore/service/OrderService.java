package com.ideas2it.dvdstore.service;

import java.util.Set;

import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.model.Order;

/**
 * <p>
 * The OrderServiceImpl is used to perform manipulations, if required.
 * </p>
 *
 * @author  Pavithra S 
 */
public interface OrderService {
  
    /**
     * <p>
     * Used to add new Order using the inputs obtained from the user
     * </p>
     *
     * @param  order  The details of the new Order to be inserted
     * @return Returns true, if inserted successfully. Otherwise, returns false
     */  
    Boolean insertOrder(Order order) throws DVDException;
    
    /**
     * <p>
     * Used to retrieve the details of a specific DVD based on ID
     * </p>
     *
     * @param  id   The DVD ID for which the details is to be obtained
     * @return Returns the DVD for the specified DVD ID. Otherwise, 
     *         returns an empty DVD object.
     */ 
    DVD retrieveByDvdId(Integer id) throws DVDException;
    
    /**
     * <p>
     * Used to display the details of all the DVDs available
     * </p>
     *
     * @param  status  Status of the Order checking whether it has been 
     *                 deleted or not
     * @return Returns the details of all the DVDs. Otherwise, 
     *         returns an empty DVD object.
     */
    Set<DVD> getDVDs(Boolean status) throws DVDException;
    
    /**
     * <p>
     * Used to display the details of all the Orders based on Customer ID
     * </p>
     *
     * @param  customerId  ID of the Customer
     * @return Returns the details of all the Orders. Otherwise, 
     *         returns an empty object.
     */
    Set<Order> getOrders(Integer customerId) throws DVDException;
    
    /**
     * <p>
     * Used to delete the details of a specific Order based on ID
     * </p>
     * 
     * @param  id   ID of the Order for which the details are to be deleted
     * @return Returns true, if deleted successfully. Otherwise, returns false
     */
    Boolean deleteOrder(Integer id) throws DVDException;
    
    /**
     * <p>
     * Used to retrieve the details of a specific Order based on Order ID.
     * </p>
     *
     * @param  id  ID of the Order for which the details is to be obtained
     * @return Returns the Order for the specified Order ID. Otherwise, 
     *         returns an empty object.
     */
    Order retrieveById(Integer id) throws DVDException;
    
    /**
     * <p>
     * Used to retrieve the details of a specific DVD based on ID
     * </p>
     *
     * @param  id   The Customer ID for which the details is to be obtained
     * @return Returns the Customer for the specified ID. Otherwise, 
     *         returns an empty Customer object.
     */
    Customer retrieveByCustomerId(Integer id) throws DVDException;
} 
