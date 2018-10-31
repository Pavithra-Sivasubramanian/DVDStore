package com.ideas2it.dvdstore.service.impl;

import java.util.Set;

import com.ideas2it.dvdstore.dao.DVDdao;
import com.ideas2it.dvdstore.dao.impl.DVDdaoImpl;
import com.ideas2it.dvdstore.model.Category;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.service.DVDService;
import com.ideas2it.dvdstore.service.CategoryService;
import com.ideas2it.dvdstore.service.impl.CategoryServiceImpl;
import com.ideas2it.dvdstore.exception.DVDException;

/**
 * <p>
 * The DVDService is used to perform manipulations, if required.
 * </p>
 *
 * @author  Pavithra S
 */
public class DVDServiceImpl implements DVDService {
    private DVDdao dvdDao = new DVDdaoImpl();

    /**
     * {@inheritDoc}
     */
    public Boolean insertDVD(DVD dvd) throws DVDException { 
        if ((null != dvd) && (null == dvdDao.isDvdExist(dvd))) {
            return dvdDao.insertDVD(dvd);
        } 
        return Boolean.FALSE;
    }

    /**
     * {@inheritDoc}
     */ 
    public DVD retrieveById(Integer id) throws DVDException {
        DVD dvd = dvdDao.retrieveById(id);
        if (dvd.getStatus()) {
            return dvd;
        }
        return new DVD();
    }

    /**
     * {@inheritDoc}
     */ 
    public Set<DVD> retrieveByName(String name) throws DVDException {
        return dvdDao.retrieveByName(name);
    }

    /**
     * {@inheritDoc}
     */ 
    public Boolean updateDVD(DVD dvd) throws DVDException {
        DVD existingDvd = isDvdExist(dvd);
        if ((null != existingDvd) && (dvd.getId() == existingDvd.getId())) {
            return dvdDao.updateDVD(dvd);
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * {@inheritDoc}
     */ 
    public Boolean deleteDVD(Integer id) throws DVDException {
        return dvdDao.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */ 
    public Set<DVD> getDVDs(Boolean status) throws DVDException { 
        return dvdDao.getDVDs(status); 
    }

    /**
     * {@inheritDoc}
     */
    public Set<Category> getCategories(Boolean status) throws DVDException { 
        CategoryService categoryService = new CategoryServiceImpl();
        return categoryService.getCategories(status); 
    }

    /**
     * {@inheritDoc}
     */ 
    public DVD isDvdExist(DVD dvd) throws DVDException {
        return dvdDao.isDvdExist(dvd);
    }

    /**
     * {@inheritDoc}
     */
    public Boolean isCategoryExistById(Integer categoryId, Boolean status) 
            throws DVDException {
        CategoryService categoryService = new CategoryServiceImpl();
        return (null != categoryService.retrieveById(categoryId, status));
    }

    /**
     * {@inheritDoc}
     */    
    public Boolean restoreDVD(Integer id) throws DVDException {
        return dvdDao.restoreDVD(id);
    }
      
    /**
     * {@inheritDoc}
     */
    public DVD getDvdByNameAndLanguage(DVD dvd, Boolean status) 
            throws DVDException {
        return dvdDao.getDvdByNameAndLanguage(dvd,status);
    }        
}
