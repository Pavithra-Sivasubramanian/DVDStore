package com.ideas2it.dvdstore.session;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.log.DVDStoreLogger;

/**
 * <p>
 * The connection to the database DVDSTORE is established in this class
 * </p> 
 *
 * @author  Pavithra S
 */
public class DVDSessionFactory {
    private static DVDSessionFactory dvdSessionFactory; 
    private static SessionFactory sessionFactory;
    private static DVDStoreLogger logger = new DVDStoreLogger();

    /**
     * <p>
     * Creation of this constructor is to prevent instantiation from 
     * any other class.
     * </p>
     */
    private DVDSessionFactory() { 
    }

    /**
     * <p>
     * Creates a single instance that can be accessed by methods in the same or 
     * different classes.
     * </p>
     *
     * @return  Returns the instance created.
     */
    public static DVDSessionFactory getInstance() {
        if (null == dvdSessionFactory) {
            dvdSessionFactory = new DVDSessionFactory();
        }
        return dvdSessionFactory;
    }

    /**
     * <p>
     * Creates the Session Factory object which in turn is used to create the 
     * Session object to get connection to the required Database which acts as  
     * the place for storing and retrieving the data.
     * </p>
     *
     * @return  Returns the Session object.
     */        
    public static Session getSession() throws DVDException {
        try {
            if (null == sessionFactory) {
                Configuration configuration = new Configuration()
                    .configure("hibernate.cfg.xml");
                sessionFactory = configuration.buildSessionFactory();
            }
        } catch (HibernateException e) {
            logger.error(Constants.MESSAGE_INITIALIZATION_FAILURE,e);
            throw new DVDException(Constants.MESSAGE_INITIALIZATION_FAILURE,e);
        }
        return sessionFactory.openSession();
    }

    /**
     * <p>
     * The Session object created has been closed
     * </p>
     */     
    public static void closeSession(Session session) throws DVDException {
        try {
            if (session != null) { 
                session.close();
            }
        } catch(HibernateException e) {
            throw new DVDException(Constants.MESSAGE_IMPROPER_CONNECTION,e);
        }
    }
}     
