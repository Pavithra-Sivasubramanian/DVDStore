package com.ideas2it.dvdstore.service.impl;

import java.util.Set;

import com.ideas2it.dvdstore.dao.CategoryDao;
import com.ideas2it.dvdstore.dao.impl.CategoryDaoImpl;
import com.ideas2it.dvdstore.model.Category;
import com.ideas2it.dvdstore.service.CategoryService;
import com.ideas2it.dvdstore.exception.DVDException;

/**
 * <p>
 * The CategoryServiceImpl is used to perform manipulations, if required.
 * </p>
 *
 * @author  Pavithra S
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * {@inheritDoc}
     */
    public Boolean insertCategory(Category category) throws DVDException { 
        if (!categoryDao.isCategoryExist(category.getName()) 
                && (null != category)) {
            return categoryDao.insertCategory(category);
        }
        return Boolean.FALSE;
    }

    /**
     * {@inheritDoc}
     */ 
    public Boolean updateCategory(Category category) throws DVDException {
        return categoryDao.updateCategory(category);
    }

    /**
     * {@inheritDoc}
     */
    public Boolean deleteCategory(Integer id) throws DVDException {
        return categoryDao.deleteCategory(id);
    }

    /**
     * {@inheritDoc}
     */
    public Set<Category> getCategories(Boolean status) throws DVDException { 
        return categoryDao.getCategories(status); 
    }

    /**
     * {@inheritDoc}
     */
    public Category retrieveById(Integer id, Boolean status) throws DVDException {
        return categoryDao.retrieveById(id,status);
    }

    /**
     * {@inheritDoc}
     */
    public Boolean restoreCategory(Integer id) throws DVDException {
        if (null != retrieveById(id, Boolean.FALSE)) {
            return categoryDao.restoreCategory(id);
        }
        return Boolean.FALSE;
    }
    
    /**
     * {@inheritDoc}
     */
    public Boolean restoreCategoryByName(String name) throws DVDException {
        return categoryDao.restoreCategoryByName(name);
    }        
}
