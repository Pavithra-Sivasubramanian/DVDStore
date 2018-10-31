package com.ideas2it.dvdstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.dao.CustomerDao;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.log.DVDStoreLogger;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.session.DVDSessionFactory;

/**
 * <p>
 * Performs operations such as adding a new Customer, retrieving the details of  
 * a specific Customer, updating the details of a specific Customer, 
 * deleting the account of a specific Customer and displaying the details of all
 * the Customers.
 * </p>
 *
 * @author  Pavithra S
 */
public class CustomerDaoImpl implements CustomerDao {
    private DVDSessionFactory dvdSessionFactory 
        = DVDSessionFactory.getInstance();
    private DVDStoreLogger logger = new DVDStoreLogger();
    
    /**
     * {@inheritDoc}
     */
    public Boolean insertCustomer(Customer customer) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_CUSTOMER_NAME + customer.getName()    
                + "\n" + Constants.MESSAGE_CUSTOMER_INSERT_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_CUSTOMER_INSERT_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Customer retrieveById(Integer customerId) throws DVDException {
        Session session = null;
        try {
            session = dvdSessionFactory.getSession();
            Customer customer = (Customer)session
                .get(Customer.class, customerId);
            return customer;
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_CUSTOMER_ID + customerId + "\n"  
                + Constants.MESSAGE_CUSTOMER_SEARCH_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_CUSTOMER_SEARCH_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
    }
    
    /**
     * {@inheritDoc}
     */
    public Customer retrieveByEmailAndPassword(String email, String  
            password, Boolean status) throws DVDException {
        Session session = null;
        Customer customer;
        try {
            session = dvdSessionFactory.getSession();
            Query query = session.createQuery
                (Constants.QUERY_GET_BY_CUSTOMER_NAME_AND_PASSWORD_AND_STATUS);
            query.setParameter(Constants.LABEL_EMAIL, email);
            query.setParameter(Constants.LABEL_PASSWORD, password);
            query.setParameter(Constants.LABEL_STATUS, status);
            customer = (Customer)query.uniqueResult();
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_CUSTOMER_EMAIL + email + "\n"
                + Constants.MESSAGE_CUSTOMER_SEARCH_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_CUSTOMER_SEARCH_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
        return customer;
    }
 
    /**
     * {@inheritDoc}
     */
    public Boolean updateCustomer(Customer customer) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            Customer oldCustomer = (Customer)session
                .get(Customer.class, customer.getId());
            oldCustomer.setName(customer.getName());
            oldCustomer.setPassword(customer.getPassword());
            oldCustomer.setContactNo(customer.getContactNo());
            oldCustomer.setEmail(customer.getEmail());
            session.update(oldCustomer);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            }
            logger.error(Constants.MESSAGE_CUSTOMER_ID + customer.getId() + "\n" 
                + Constants.MESSAGE_CUSTOMER_UPDATE_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_CUSTOMER_UPDATE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }
    
    /**
     * {@inheritDoc}
     */  
    public Boolean deleteCustomer(Integer customerId) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            Customer customer = (Customer)session.get(Customer.class,customerId);
            customer.setStatus(Boolean.FALSE);
            session.update(customer);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_CUSTOMER_ID + customerId + "\n" 
                + Constants.MESSAGE_CUSTOMER_DELETE_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_CUSTOMER_DELETE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    } 
      
    /**
     * {@inheritDoc}
     */
    public Set<Customer> getCustomers(Boolean status) throws DVDException {
        Session session = null;
        Set<Customer> customers;
        try {
            session = dvdSessionFactory.getSession();
            Query query = session.createQuery
                (Constants.QUERY_GET_BY_CUSTOMER_STATUS);
            query.setParameter(Constants.LABEL_STATUS, status);
            List<Customer> list = query.getResultList();
            customers = new LinkedHashSet<Customer>(list);
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_CUSTOMERS_DISPLAY_FAILURE,e);
            throw new DVDException
                (Constants.MESSAGE_CUSTOMERS_DISPLAY_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
        return customers;    
    }
}
