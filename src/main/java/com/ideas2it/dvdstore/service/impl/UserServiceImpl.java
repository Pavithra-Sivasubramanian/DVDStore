package com.ideas2it.dvdstore.service.impl;

import java.util.Set;

import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.dao.UserDao;
import com.ideas2it.dvdstore.dao.impl.UserDaoImpl;
import com.ideas2it.dvdstore.service.DVDService;
import com.ideas2it.dvdstore.service.CustomerService;
import com.ideas2it.dvdstore.service.UserService;
import com.ideas2it.dvdstore.service.impl.CustomerServiceImpl;
import com.ideas2it.dvdstore.service.impl.DVDServiceImpl;

/**
 * <p>
 * The UserServiceImpl is used to perform manipulations, if required.
 * </p>
 *
 * @author  Pavithra S
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    private DVDService dvdService = new DVDServiceImpl();
    private CustomerService customerService = new CustomerServiceImpl();
 
    /**
     * {@inheritDoc}
     */
    public User retrieveByEmailAndPassword(String email, String password,  
            Boolean status) throws DVDException {
        return userDao.retrieveByEmailAndPassword(email, password, status);
    }

    /**
     * {@inheritDoc}
     */
    public Set<DVD> getDvds(Boolean status) throws DVDException { 
        return dvdService.getDVDs(status); 
    }
    
    /**
     * {@inheritDoc}
     */
    public Customer retrieveCustomer(String email, String password,  
            Boolean status) throws DVDException {
        return customerService.retrieveByEmailAndPassword(email, password, status);
    }
    
    /**
     * {@inheritDoc}
     */
    public Boolean insertCustomer(Customer customer) throws DVDException { 
        return customerService.insertCustomer(customer);
    }
}
