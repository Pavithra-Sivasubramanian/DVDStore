package com.ideas2it.dvdstore.service;

import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.model.Customer;

public interface AddressService {
  
    /**
     * <p>
     * Used to include new Address using the inputs obtained from the user
     * </p>
     *
     * @param  address  The new Address to be inserted
     * @return Returns true, if inserted successfully. Otherwise, returns false
     */ 
    Boolean insertAddress(Address address) throws DVDException;
    
    /**
     * <p>
     * Used to delete the details of a specific Address based on ID
     * </p>
     * 
     * @param  id  ID of the Address
     * @return Returns true, if deleted successfully. Otherwise, returns false.      
     */
    Boolean deleteAddress(Integer id, Integer customerId) throws DVDException;
    
    /**
     * <p>
     * Used to make changes in the Address details for the ID specified by 
     * the user
     * </p>
     * 
     * @param   address    The altered Address details
     * @return  Returns true, if updated successfully. Otherwise, returns false. 
     */ 
    Boolean updateAddress(Address address) throws DVDException;
    
    /**
     * <p>
     * Used to retrieve the details of a specific Address based on ID.
     * </p>
     *
     * @param  addressId  ID of the Address for which the details is to be 
                          obtained
     * @return Returns Address for the ID specified. Otherwise, returns an empty 
     *         Address object.
     */
    Address retrieveById(Integer id) throws DVDException;

    /**
     * <p>
     * Used to retrieve the details of the Customer for the ID specified
     * </p>
     *
     * @param   customerId  ID of the Customer for which the Customer is to be 
     *                     obtained
     * @return  Returns the Customer for the ID specified. Otherwise, returns an 
     *          empty object
     */
    Customer retrieveByCustomerId(Integer customerId) throws DVDException;
}
