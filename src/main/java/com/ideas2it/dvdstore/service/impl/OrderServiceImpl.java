package com.ideas2it.dvdstore.service.impl;

import java.util.Set;

import com.ideas2it.dvdstore.dao.OrderDao;
import com.ideas2it.dvdstore.dao.impl.OrderDaoImpl;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.model.Order;
import com.ideas2it.dvdstore.service.CustomerService;
import com.ideas2it.dvdstore.service.DVDService;
import com.ideas2it.dvdstore.service.impl.CustomerServiceImpl;
import com.ideas2it.dvdstore.service.impl.DVDServiceImpl;
import com.ideas2it.dvdstore.service.OrderService;
import com.ideas2it.dvdstore.exception.DVDException;

/**
 * <p>
 * The DVDService is used to perform manipulations, if required.
 * </p>
 *
 * @author  Pavithra S
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();

    /**
     * {@inheritDoc}
     */
    public Boolean insertOrder(Order order) throws DVDException { 
        return orderDao.insertOrder(order);
    }
    
    /**
     * {@inheritDoc}
     */
    public DVD retrieveByDvdId(Integer id) throws DVDException {
        DVDService dvdService = new DVDServiceImpl();
        return dvdService.retrieveById(id);
    }
    
    /**
     * {@inheritDoc}
     */ 
    public Set<DVD> getDVDs(Boolean status) throws DVDException { 
        DVDService dvdService = new DVDServiceImpl();
        return dvdService.getDVDs(status); 
    }
    
    /**
     * {@inheritDoc}
     */ 
    public Set<Order> getOrders(Integer customerId) 
            throws DVDException { 
        return orderDao.getOrders(customerId); 
    }
    
    /**
     * {@inheritDoc}
     */ 
    public Boolean deleteOrder(Integer id) throws DVDException {
        return orderDao.deleteOrder(id);
    }
    
    /**
     * {@inheritDoc}
     */ 
    public Order retrieveById(Integer id) throws DVDException {
        return orderDao.retrieveById(id);
    }
    
    /**
     * {@inheritDoc}
     */
    public Customer retrieveByCustomerId(Integer id) throws DVDException {
        CustomerService customerService = new CustomerServiceImpl();
        return customerService.retrieveById(id);
    }
}
