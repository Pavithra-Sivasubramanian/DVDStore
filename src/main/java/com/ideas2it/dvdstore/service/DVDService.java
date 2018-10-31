package com.ideas2it.dvdstore.service;

import java.util.List;
import java.util.Set;

import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.model.Category;
import com.ideas2it.dvdstore.model.DVD;

/**
 * <p>
 * Contains the various operations in DVD Service Implementation layer
 * </p>
 *
 * @author  Pavithra S 
 */
public interface DVDService {
  
    /**
     * <p>
     * Used to add new DVD using the inputs obtained from the user
     * </p>
     *
     * @param  dvd  The new DVD details to be inserted
     * @return Returns true, if inserted successfully. Otherwise, returns false
     */ 
    Boolean insertDVD(DVD dvd) throws DVDException;

    /**
     * <p>
     * Used to retrieve the details of a specific DVD from the collecton of 
     * DVD's based on ID.
     * </p>
     *
     * @param  id  The details of the DVD for the ID specified
     * @return Returns the DVD for the specified DVD ID. Otherwise, 
     *         returns an empty DVD object.
     */ 
    DVD retrieveById(Integer id) throws DVDException;

    /**
     * <p>
     * Used to retrieve the details of a specific DVD from the collecton of 
     * DVD's based on Name.
     * </p>
     *
     * @param  name  The DVD Name for which the details is to be obtained
     * @return Returns the list of DVDs for the specified DVD Name. Otherwise, 
     *         returns an empty DVD object.
     */
    Set<DVD> retrieveByName(String name) throws DVDException;

    /**
     * <p>
     * Used to make changes in the DVD details for the ID specified by the user
     * </p>
     * 
     * @param dvd   Altered DVD details 
     * @return  Returns true, if updated successfully. Otherwise, returns false.
     */
    Boolean updateDVD(DVD dvd) throws DVDException;

    /**
     * <p>
     * Used to delete the details of a specific DVD based on ID
     * </p>
     * 
     * @param   id  The DVD ID based on which its details are deleted
     * @return  Returns true, if deleted successfully. Otherwise, returns false
     */
    Boolean deleteDVD(Integer id) throws DVDException;

    /**
     * <p>
     * Used to display the details of all the DVD's in the List
     * </p>
     *
     * @param  status   Status of the DVD checking whether it has been 
     *                  deleted or not
     * @return Returns the details of all the DVDs. Otherwise, 
     *         returns an empty DVD object.
     */
    Set<DVD> getDVDs(Boolean status) throws DVDException;

    /**
     * <p>
     * Used to get all the Categories available
     * </p>
     *
     * @param  status   Status of the DVD checking whether it has been 
     *                  deleted or not
     * @return Returns the Categories of the DVD
     */
    Set<Category> getCategories(Boolean status) throws DVDException; 
    
    /**
     * <p> 
     * Compares the details of the new DVD to be entered with existing DVD's 
     * to check if its already available.
     * </p>
     *
     * @param  dvd  The details of the new DVD to be entered
     * @return Returns false, if the value of both the DVD name and language are 
     *         same. Otherwise, returns true.
     */    
    DVD isDvdExist(DVD dvd) throws DVDException;

    /**
     * <p>
     * Checks whether the Category ID specified by the user exists in the 
     * Category table
     * </p>
     *
     * @param  categoryId  The ID of the Category which is to be checked if 
     *                     it exists 
     * @param  status      Status of the DVD checking whether it has been 
     *                     deleted or not
     * @return Returns true, if the Category specified is available. Otherwise, 
     *         returns false.
     */     
    Boolean isCategoryExistById(Integer categoryId, Boolean status) 
            throws DVDException;

    /**
     * <p>
     * Used to restore the details of a specific DVD based on ID
     * </p>
     * 
     * @param  id  The DVD ID based on which its details are deleted
     * @return Returns true, if restored successfully. Otherwise, returns false
     */      
    Boolean restoreDVD(Integer id) throws DVDException;

    /** 
     * <p>
     * Used to retrieve the DVD of the specified DVD Name and Language
     * </p>
     *
     * @param  dvd  Details of the new DVD to be inserted
     * @param  status   Status of the DVD checking whether it has been 
     *                  deleted or not
     * @return Returns the object of the DVD
     */
    DVD getDvdByNameAndLanguage(DVD dvd, Boolean status) throws DVDException;
}
