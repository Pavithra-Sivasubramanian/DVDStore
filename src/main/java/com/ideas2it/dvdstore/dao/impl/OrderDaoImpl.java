package com.ideas2it.dvdstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.dao.OrderDao;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.log.DVDStoreLogger;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.model.Order;
import com.ideas2it.dvdstore.session.DVDSessionFactory;

/**
 * <p>
 * Performs operations such as creating a new Order, deleting a specific Order, 
 * displaying the details of the Orders for a specific Customer and retrieving
 * the details of a specific Order 
 * </p>
 *
 * @author  Pavithra S
 */
public class OrderDaoImpl implements OrderDao {
    private DVDSessionFactory dvdSessionFactory 
        = DVDSessionFactory.getInstance();
    private DVDStoreLogger logger = new DVDStoreLogger();
    
    /**
     * {@inheritDoc}
     */
    public Boolean insertOrder(Order order) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            session.save(order);
            DVD dvd = order.getDvd();
            dvd.setQuantity(dvd.getQuantity() - order.getQuantity());
            session.update(dvd);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            DVD dvd = order.getDvd();
            logger.error(Constants.MESSAGE_DVD_NAME + dvd.getName() + "\n"    
                + Constants.MESSAGE_ORDER_INSERT_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_ORDER_INSERT_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public Set<Order> getOrders(Integer customerId) 
            throws DVDException {
        Session session = null;
        Set<Order> orders;
        try {
            session = dvdSessionFactory.getSession();
            Query query = session.createQuery
                (Constants.QUERY_GET_BY_CUSTOMER_ID);
            query.setParameter(Constants.LABEL_CUSTOMER_ID, customerId);
            List<Order> list = query.getResultList();
            orders = new LinkedHashSet<Order>(list);
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_ORDERS_DISPLAY_FAILURE,e);
            throw new DVDException
                (Constants.MESSAGE_ORDERS_DISPLAY_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
        return orders; 
    }
    
    /**
     * {@inheritDoc}
     */  
    public Boolean deleteOrder(Integer orderId) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            Order order = (Order)session.get(Order.class, orderId);
            Integer orderQuantity = order.getQuantity();
            DVD dvd = order.getDvd();
            dvd.setQuantity(dvd.getQuantity() + order.getQuantity());
            session.update(dvd);
            session.delete(order);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_DVD_ID + orderId + "\n" 
                + Constants.MESSAGE_ORDER_DELETE_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_ORDER_DELETE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public Order retrieveById(Integer orderId) throws DVDException {
        Session session = null;
        try {
            session = dvdSessionFactory.getSession();
            Order order = (Order)session.get(Order.class, orderId);
            return order;
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_DVD_ID + orderId + "\n"  
                + Constants.MESSAGE_ORDER_SEARCH_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_ORDER_SEARCH_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
    }
}
