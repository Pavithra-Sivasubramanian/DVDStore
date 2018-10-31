package com.ideas2it.dvdstore.service.impl;

import java.util.Set;

import com.ideas2it.dvdstore.dao.CustomerDao;
import com.ideas2it.dvdstore.dao.impl.CustomerDaoImpl;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.service.CustomerService;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.service.DVDService;
import com.ideas2it.dvdstore.service.impl.DVDServiceImpl;

/**
 * <p>
 * The CustomerService is used to perform manipulations, if required.
 * </p>
 *
 * @author  Pavithra S
 */
public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = new CustomerDaoImpl();
    private DVDService dvdService = new DVDServiceImpl();

    /**
     * {@inheritDoc}
     */
    public Boolean insertCustomer(Customer customer) throws DVDException { 
        if (null == retrieveByEmailAndPassword(customer.getEmail(), 
                customer.getPassword(), customer.getStatus())) {
            return customerDao.insertCustomer(customer);
        }
        return Boolean.FALSE;
    }
    
    /**
     * {@inheritDoc}
     */
    public Customer retrieveById(Integer id) throws DVDException {
        return customerDao.retrieveById(id);
    }
    
    /**
     * {@inheritDoc}
     */
    public Customer retrieveByEmailAndPassword(String email, String password,  
            Boolean status) throws DVDException {
        return customerDao.retrieveByEmailAndPassword(email, password, status);
    }
    
    /**
     * {@inheritDoc}
     */ 
    public Boolean updateCustomer(Customer customer) throws DVDException {
        /*Customer existingCustomer = retrieveByEmailAndPassword
            (customer.getEmail(), customer.getPassword(), Boolean.TRUE);
        if ((null != existingCustomer) && 
                (customer.getId() == existingCustomer.getId())) {*/
            return customerDao.updateCustomer(customer);
/*        }
        return Boolean.FALSE;
*/    }
    
    /**
     * {@inheritDoc}
     */
    public Boolean deleteCustomer(Integer id) throws DVDException {
        return customerDao.deleteCustomer(id);
    }
    
    /**
     * {@inheritDoc}
     */
    public Set<Customer> getCustomers(Boolean status) throws DVDException { 
        return customerDao.getCustomers(status); 
    }
    
    /**
     * {@inheritDoc}
     */
    public Set<DVD> getDvds(Boolean status) throws DVDException { 
        return dvdService.getDVDs(status); 
    }
}
