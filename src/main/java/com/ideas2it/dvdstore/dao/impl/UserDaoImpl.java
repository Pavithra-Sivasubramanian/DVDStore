package com.ideas2it.dvdstore.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.log.DVDStoreLogger;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.dao.UserDao;
import com.ideas2it.dvdstore.session.DVDSessionFactory;

/**
 * <p>
 * The UserController gets the inputs from the user on which the operations 
 * such as login and logout are performed.
 * </p>
 */
public class UserDaoImpl implements UserDao {
    private DVDSessionFactory dvdSessionFactory 
        = DVDSessionFactory.getInstance();
    private DVDStoreLogger logger = new DVDStoreLogger();
    
    /**
     * {@inheritDoc}
     */
    public User retrieveByEmailAndPassword(String userName, String password, 
            Boolean status) throws DVDException {
        Session session = null;
        User user;
        try {
            session = dvdSessionFactory.getSession();
            Query query = session.createQuery
                (Constants.QUERY_GET_BY_USER_NAME_AND_PASSWORD_AND_STATUS);
            query.setParameter(Constants.LABEL_USERNAME, userName);
            query.setParameter(Constants.LABEL_PASSWORD, password);
            query.setParameter(Constants.LABEL_STATUS, status);
            user = (User)query.uniqueResult();
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_CUSTOMER_EMAIL + userName + "\n"
                + Constants.MESSAGE_CUSTOMER_SEARCH_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_CUSTOMER_SEARCH_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
        return user;
    }
}
