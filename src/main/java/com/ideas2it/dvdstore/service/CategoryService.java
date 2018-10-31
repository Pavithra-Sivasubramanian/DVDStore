package com.ideas2it.dvdstore.service;

import java.util.Set;

import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.model.Category;

/**
 * <p>
 * The CategoryServiceImpl is used to perform manipulations, if required.
 * </p>
 *
 * @author  Pavithra S
 */
public interface CategoryService {

    /**
     * <p>
     * Used to add new Category to the list using the inputs obtained from user
     * </p>
     *
     * @param  category  The new Category to be inserted
     * @return Returns true if the Category details have been successfully 
     *         inserted. Otherwise, returns false.
     */
    Boolean insertCategory(Category category) throws DVDException;

    /**
     * <p>
     * Used to make changes in the Category for the ID specified by the user
     * </p>
     * 
     * @param  category  Altered Category details
     * @return Returns true, if updated successfully. Otherwise, returns false. 
     */
    Boolean updateCategory(Category category) throws DVDException;

    /**
     * <p>
     * Used to delete the details of a specific Category based on ID
     * </p>
     * 
     * @param  id  The Category ID based on which its details are deleted
     * @return Returns true, if deleted successfully. Otherwise, returns false.      
     */
    Boolean deleteCategory(Integer id) throws DVDException;

    /**
     * <p>
     * Used to display the details of all the Categories in the List
     * </p>
     *
     * @param  status  Status of the Category checking whether it has been 
     *                 deleted or not
     * @return Returns the details of all the Categories. Otherwise, 
     *         returns an empty String object.
     */
    Set<Category> getCategories(Boolean status) throws DVDException;

    /**
     * <p>
     * Used to retrieve the details of a specific Category based on Category ID. 
     * </p>
     *
     * @param  id  The Category ID for which the details is to be obtained
     * @param  status  Status of the Category checking whether it has been 
     *                 deleted or not
     * @return Returns the Category for the specified Category ID. Otherwise, 
     *         returns an empty Category object.
     */
    Category retrieveById(Integer id, Boolean status) throws DVDException;

    /**
     * <p>
     * Used to restore the details of a specific Category based on ID
     * </p>
     * 
     * @param  id  The Category ID based on which its details are restored
     * @return Returns true, if restored successfully. Otherwise, returns false.      
     */      
    Boolean restoreCategory(Integer id) throws DVDException;  

    /**
     * <p>
     * Used to restore the details of a specific Category based on ID
     * </p>
     * 
     * @param  name  The Category ID based on which its details are restored
     * @return Returns true, if restored successfully. Otherwise, returns false. 
     */     
    Boolean restoreCategoryByName(String name) throws DVDException;      
}
