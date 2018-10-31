package com.ideas2it.dvdstore.dao;

import java.util.Set;

import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.model.Order;

/**
 * <p>
 * Performs the various CRUD operations such as Create, Display, search and 
 * Delete Orders 
 * </p>
 *
 * @author  Pavithra S 
 */
public interface OrderDao {

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
     * @param  orderId   ID of the Order for which the details are to be deleted
     * @return Returns true, if deleted successfully. Otherwise, returns false
     */
    Boolean deleteOrder(Integer orderId) throws DVDException;
    
    /**
     * <p>
     * Used to retrieve the details of a specific Order based on Order ID.
     * </p>
     *
     * @param  orderId  ID of the Order for which the details is to be obtained
     * @return Returns the Order for the specified Order ID. Otherwise, 
     *         returns an empty object.
     */
    Order retrieveById(Integer orderId) throws DVDException;
}
