package com.ideas2it.dvdstore.service.impl;

import java.util.Set;

import com.ideas2it.dvdstore.dao.AddressDao;
import com.ideas2it.dvdstore.dao.impl.AddressDaoImpl;
import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.Order;
import com.ideas2it.dvdstore.service.AddressService;
import com.ideas2it.dvdstore.service.CustomerService;
import com.ideas2it.dvdstore.service.impl.CustomerServiceImpl;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.log.DVDStoreLogger;

public class AddressServiceImpl implements AddressService {
    private AddressDao addressDao = new AddressDaoImpl();
    CustomerService customerService = new CustomerServiceImpl();
    private DVDStoreLogger logger = new DVDStoreLogger(); 

    /**
     * {@inheritDoc}
     */
    public Boolean insertAddress(Address address) throws DVDException { 
        if (null != address) {
            return addressDao.insertAddress(address);
        } 
        return Boolean.FALSE;
    }
    
    /**
     * {@inheritDoc}
     */ 
    public Boolean deleteAddress(Integer id, Integer customerId) 
            throws DVDException {
        Customer customer = customerService.retrieveById(customerId);
        Boolean flag = Boolean.FALSE;
        if (null != customer) { 
            Set<Order> orders = customer.getOrders();
            for (Order order: orders) {
                Address address = order.getAddress();
                if (id == address.getId()) {
                    return Boolean.FALSE;
                } 
            }
            return addressDao.deleteAddress(id);
        }
        return Boolean.FALSE;
    }
    
    /**
     * {@inheritDoc}
     */ 
    public Boolean updateAddress(Address address) 
            throws DVDException {
        return addressDao.updateAddress(address);
    }
    
    /**
     * {@inheritDoc}
     */
    public Address retrieveById(Integer id) throws DVDException {
        return addressDao.retrieveById(id);
    }
    
    /**
     * {@inheritDoc}
     */
    public Customer retrieveByCustomerId(Integer customerId) throws DVDException {
        return customerService.retrieveById(customerId);
    }
}
