package com.ideas2it.dvdstore.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.dao.AddressDao;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.log.DVDStoreLogger;
import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.session.DVDSessionFactory;

/**
 * <p>
 * Performs the various CRUD operations such as Add, Retrieve, Update and Delete 
 * Address
 * </p>
 *
 * @author  Pavithra S
 */
public class AddressDaoImpl implements AddressDao {
    private DVDSessionFactory dvdSessionFactory 
        = DVDSessionFactory.getInstance();
    private DVDStoreLogger logger = new DVDStoreLogger();
    
    /**
     * {@inheritDoc}
     */
    public Boolean insertAddress(Address address) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            session.save(address);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_ADDRESS_ID + address.getId()    
                + "\n" + Constants.MESSAGE_ADDRESS_INSERT_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_ADDRESS_INSERT_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }
    
    /**
     * {@inheritDoc}
     */  
    public Boolean deleteAddress(Integer addressId) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            Address address = (Address)session.get(Address.class,addressId);
            session.delete(address);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_ADDRESS_ID + addressId + "\n" 
                + Constants.MESSAGE_ADDRESS_DELETE_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_ADDRESS_DELETE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    } 
    
    /**
     * {@inheritDoc}
     */
    public Boolean updateAddress(Address address) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            Address oldAddress = (Address)session
                .get(Address.class, address.getId());
            oldAddress.setStreet(address.getStreet());
            oldAddress.setCity(address.getCity());
            oldAddress.setZipcode(address.getZipcode());
            oldAddress.setState(address.getState());
            session.update(oldAddress);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            }
            logger.error(Constants.MESSAGE_ADDRESS_ID + address.getId() + "\n" 
                + Constants.MESSAGE_ADDRESS_UPDATE_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_ADDRESS_UPDATE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public Address retrieveById(Integer addressId) throws DVDException {
        Session session = null;
        try {
            session = dvdSessionFactory.getSession();
            Address address = (Address)session.get(Address.class,addressId);
            return address;
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_ADDRESS_ID + addressId + "\n"  
                + Constants.MESSAGE_ADDRESS_SEARCH_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_ADDRESS_SEARCH_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
    }
}
