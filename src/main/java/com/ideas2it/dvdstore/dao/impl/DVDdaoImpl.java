package com.ideas2it.dvdstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.dao.DVDdao;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.log.DVDStoreLogger;
import com.ideas2it.dvdstore.model.Category;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.session.DVDSessionFactory;

/**
 * <p>
 * Performs operations such as adding a new DVD, retrieving the details of a
 * specific DVD, updating the details of the DVD, deleting a specific DVD 
 * and displaying the details of all the DVD's.
 * </p>
 *
 * @author  Pavithra S
 */
public class DVDdaoImpl implements DVDdao {
    private DVDSessionFactory dvdSessionFactory 
        = DVDSessionFactory.getInstance();
    private DVDStoreLogger logger = new DVDStoreLogger();
    
    /**
     * {@inheritDoc}
     */
    public Boolean insertDVD(DVD dvd) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            session.save(dvd);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_DVD_NAME + dvd.getName() + "\n"    
                + Constants.MESSAGE_DVD_INSERT_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_DVD_INSERT_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }

    /**
     * {@inheritDoc}
     */
    public DVD retrieveById(Integer dvdId) throws DVDException {
        Session session = null;
        try {
            session = dvdSessionFactory.getSession();
            session.enableFilter(Constants.LABEL_CATEGORY_STATUS)
                .setParameter(Constants.LABEL_STATUS, Boolean.TRUE);
            DVD dvd = (DVD)session.get(DVD.class, dvdId);
            session.disableFilter(Constants.LABEL_CATEGORY_STATUS);
            return dvd;
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_DVD_ID + dvdId + "\n"  
                + Constants.MESSAGE_DVD_SEARCH_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_DVD_SEARCH_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
    }

    /**
     * {@inheritDoc}
     */
    public Set<DVD> retrieveByName(String dvdName) throws DVDException {
        Session session = null;
        Set<DVD> dvds;
        try {
            session = dvdSessionFactory.getSession();
            session.enableFilter(Constants.LABEL_CATEGORY_STATUS)
                .setParameter(Constants.LABEL_STATUS, Boolean.TRUE);
            Query query = session.createQuery
                (Constants.QUERY_GET_BY_DVD_NAME_AND_STATUS);
            query.setParameter(Constants.LABEL_NAME, dvdName);
            query.setParameter(Constants.LABEL_STATUS, Boolean.TRUE);
            List<DVD> list = query.getResultList();
            dvds = new LinkedHashSet<DVD>(list);
            session.disableFilter(Constants.LABEL_CATEGORY_STATUS);
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_DVD_NAME + dvdName + "\n"
                + Constants.MESSAGE_DVD_SEARCH_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_DVD_SEARCH_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
        return dvds;
    }

    /**
     * {@inheritDoc}
     */
    public Boolean updateDVD(DVD dvd) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            DVD oldDvd = (DVD)session.get(DVD.class, dvd.getId());
            oldDvd.setName(dvd.getName());
            oldDvd.setLanguage(dvd.getLanguage());
            oldDvd.setGenre(dvd.getGenre());
            oldDvd.setRating(dvd.getRating());
            oldDvd.setQuantity(dvd.getQuantity());
            oldDvd.setReleaseDate(dvd.getReleaseDate());
            oldDvd.setPrice(dvd.getPrice());
            oldDvd.setGenre(dvd.getGenre());
            session.update(oldDvd);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            }
            logger.error(Constants.MESSAGE_DVD_ID + dvd.getId() + "\n" 
                + Constants.MESSAGE_DVD_UPDATE_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_DVD_UPDATE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }
  
    /**
     * {@inheritDoc}
     */  
    public Boolean deleteById(Integer dvdId) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            DVD dvd = (DVD)session.get(DVD.class, dvdId);
            dvd.setStatus(Boolean.FALSE);
            session.update(dvd);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_DVD_ID + dvdId + "\n" 
                + Constants.MESSAGE_DVD_DELETE_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_DVD_DELETE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }     

    /**
     * {@inheritDoc}
     */
    public Set<DVD> getDVDs(Boolean status) throws DVDException {
        Session session = null;
        Set<DVD> dvds;
        try {
            session = dvdSessionFactory.getSession();
            session.enableFilter(Constants.LABEL_CATEGORY_STATUS)
                .setParameter(Constants.LABEL_STATUS,Boolean.TRUE);                
            Query query = session.createQuery
                (Constants.QUERY_GET_BY_DVD_STATUS);
            query.setParameter(Constants.LABEL_STATUS, status);
            List<DVD> list = query.getResultList();
            dvds = new LinkedHashSet<DVD>(list);
            session.disableFilter(Constants.LABEL_CATEGORY_STATUS);
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_DVDS_DISPLAY_FAILURE,e);
            throw new DVDException
                (Constants.MESSAGE_DVDS_DISPLAY_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
        return dvds; 
    }

    /**
     * {@inheritDoc}
     */
    public DVD isDvdExist(DVD dvd) throws DVDException {
        Session session = null;
        DVD newDvd;
        try {
            session = dvdSessionFactory.getSession();
            session = dvdSessionFactory.getSession();
            session.enableFilter(Constants.LABEL_CATEGORY_STATUS)
                .setParameter(Constants.LABEL_STATUS, Boolean.TRUE);
            Query query = session.createQuery
                (Constants.QUERY_GET_BY_DVD_NAME_AND_LANGUSGE);
            query.setParameter(Constants.LABEL_NAME, dvd.getName());
            query.setParameter(Constants.LABEL_LANGUAGE, dvd.getLanguage());
            newDvd = (DVD)query.uniqueResult();
            session.disableFilter(Constants.LABEL_CATEGORY_STATUS);
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_DVD_NAME + dvd.getName() + "\n" 
                + Constants.MESSAGE_DVD_SEARCH_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_DVD_SEARCH_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
        return newDvd;
    }

    /**
     * {@inheritDoc}
     */    
    public Boolean restoreDVD(Integer dvdId) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            DVD dvd = (DVD)session
                .get(DVD.class, dvdId);
            dvd.setStatus(Boolean.TRUE);
            session.update(dvd);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_DVD_ID + dvdId + "\n" 
                + Constants.MESSAGE_DVD_RESTORE_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_DVD_RESTORE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }

    /**
     * {@inheritDoc}
     */
    public DVD getDvdByNameAndLanguage(DVD dvd, Boolean status) 
            throws DVDException {
        Session session = null;
        DVD newDvd;
        try {
            session = dvdSessionFactory.getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<DVD> criteria = builder
                .createQuery(DVD.class);
            Root<DVD> root = criteria.from(DVD.class);
            Predicate name = builder.equal(root
                .get(Constants.LABEL_NAME),dvd.getName());
            Predicate language = builder.equal
                (root.get(Constants.LABEL_LANGUAGE),dvd.getLanguage());            
            Predicate dvdStatus = builder.equal(root
                .get(Constants.LABEL_STATUS),status);
            criteria.select(root).where(builder.and(name,language,dvdStatus));
            Query<DVD> query = session.createQuery(criteria);
            newDvd = query.uniqueResult();      
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_DVD_NAME + dvd.getName() + "\n" 
                + Constants.MESSAGE_DVD_SEARCH_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_DVD_SEARCH_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
        return newDvd;
    }
}
