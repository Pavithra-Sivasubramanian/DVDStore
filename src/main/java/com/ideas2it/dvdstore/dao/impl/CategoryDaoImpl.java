package com.ideas2it.dvdstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction; 

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.dao.CategoryDao;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.log.DVDStoreLogger;
import com.ideas2it.dvdstore.model.Category;
import com.ideas2it.dvdstore.session.DVDSessionFactory;

/**
 * <p>
 * Performs the various CRUD operations such as Add, Display, Update, Delete and
 * Restore of Categories
 * </p>
 *
 * @author  Pavithra S
 */
public class CategoryDaoImpl implements CategoryDao{
    private DVDSessionFactory dvdSessionFactory 
        = DVDSessionFactory.getInstance();
    private DVDStoreLogger logger = new DVDStoreLogger();               

    /**
     * {@inheritDoc}
     */
    public Boolean insertCategory(Category category) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_CATEGORY_NAME + category.getName()     
                + "\n" + Constants.MESSAGE_CATEGORY_INSERT_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_CATEGORY_INSERT_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Set<Category> getCategories(Boolean status) throws DVDException {
        Session session = null;
        Set<Category> categories;
        try {
            session = dvdSessionFactory.getSession();
            session.enableFilter(Constants.LABEL_DVD_STATUS)
                .setParameter(Constants.LABEL_STATUS,Boolean.TRUE);
            Query query = session.createQuery
                (Constants.QUERY_GET_BY_CATEGORY_STATUS);
            query.setParameter(Constants.LABEL_STATUS, status);
            List<Category> list = query.getResultList();
            categories = new LinkedHashSet<Category>(list);
            session.disableFilter(Constants.LABEL_DVD_STATUS);
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_CATEGORIES_DISPLAY_FAILURE,e);
            throw new DVDException
                (Constants.MESSAGE_CATEGORIES_DISPLAY_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
        return categories;    
    }

    /**
     * {@inheritDoc}
     */
    public Boolean updateCategory(Category newCategory) 
            throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            Category category = (Category)session
                .get(Category.class, newCategory.getId());
            category.setName(newCategory.getName());
            session.update(category);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            }
            logger.error(Constants.MESSAGE_CATEGORY_ID + newCategory.getId() + 
                "\n" + Constants.MESSAGE_CATEGORY_UPDATE_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_CATEGORY_UPDATE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Boolean deleteCategory(Integer categoryId) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            Category category = (Category)session
                .get(Category.class, categoryId);
            category.setStatus(Boolean.FALSE);
            session.update(category);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_CATEGORY_ID + categoryId + "\n" 
                + Constants.MESSAGE_CATEGORY_DELETE_FAILURE, e);
            throw new DVDException(Constants
                .MESSAGE_CATEGORY_DELETE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }     

    /**
     * {@inheritDoc}
     */
    public Category retrieveById(Integer categoryId, Boolean status) 
            throws DVDException {
        Session session = null;
        try {
            session = dvdSessionFactory.getSession();
            session.enableFilter(Constants.LABEL_DVD_STATUS)
                .setParameter(Constants.LABEL_STATUS, Boolean.TRUE);
            Category category = (Category)session.get(Category.class,categoryId);
            session.disableFilter(Constants.LABEL_DVD_STATUS);
            if (status == category.getStatus()) {
                return category;
            } else {
                return null;
            }        
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_CATEGORY_NOT_AVAILABLE,e);
            throw new DVDException
                (Constants.MESSAGE_CATEGORY_NOT_AVAILABLE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
    }

    /**
     * {@inheritDoc}
     */
    public Boolean isCategoryExist(String categoryName) throws DVDException { 
        Session session = null;
        try {
            session = dvdSessionFactory.getSession();
            session.enableFilter(Constants.LABEL_DVD_STATUS)
                .setParameter(Constants.LABEL_STATUS, Boolean.TRUE);
            Query query = session.createQuery
                (Constants.QUERY_GET_BY_CATEGORY_NAME);
            query.setParameter(Constants.LABEL_NAME, categoryName);
            Category category = (Category)query.uniqueResult();
            return (null != category);
        } catch (HibernateException e) {       
            logger.error(Constants.MESSAGE_CATEGORY_NAME + categoryName + "\n"    
                + Constants.MESSAGE_CATEGORY_SEARCH_FAILURE, e);
            throw new DVDException
                (Constants.MESSAGE_CATEGORY_SEARCH_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }    
    }

    /**
     * {@inheritDoc}
     */
    public Boolean restoreCategory(Integer categoryId) throws DVDException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dvdSessionFactory.getSession();
            transaction = session.beginTransaction();
            Category category = (Category)session
                .get(Category.class, categoryId);
            category.setStatus(Boolean.TRUE);
            session.update(category);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            } 
            logger.error(Constants.MESSAGE_CATEGORY_ID + categoryId + "\n" 
                + Constants.MESSAGE_CATEGORY_RESTORE_FAILURE, e);
            throw new DVDException(Constants
                .MESSAGE_CATEGORY_RESTORE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    } 

    /**
     * {@inheritDoc}
     */    
    public Boolean restoreCategoryByName(String categoryName) 
            throws DVDException { 
        Session session = null;
        try {
            session = dvdSessionFactory.getSession();
            Query query = session.createQuery
                (Constants.QUERY_GET_BY_CATEGORY_NAME);
            query.setParameter(Constants.LABEL_NAME, categoryName);
            Category category = (Category)query.uniqueResult();
            if (null != category) {
                restoreCategory(category.getId());
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (HibernateException e) {
            logger.error(Constants.MESSAGE_CATEGORY_NAME + categoryName + "\n"    
                + Constants.MESSAGE_CATEGORY_RESTORE_FAILURE, e);
            throw new DVDException(Constants
                .MESSAGE_CATEGORY_RESTORE_FAILURE + e.getMessage());
        } finally {
            dvdSessionFactory.closeSession(session);
        }
    }  
}
