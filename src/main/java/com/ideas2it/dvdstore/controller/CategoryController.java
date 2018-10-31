package com.ideas2it.dvdstore.controller;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.servlet.ModelAndView;  

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.log.DVDStoreLogger;
import com.ideas2it.dvdstore.model.Category;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.service.CategoryService;
import com.ideas2it.dvdstore.service.impl.CategoryServiceImpl;

/**
 * <p>
 * The CategoryController gets the inputs from the user on which the operations 
 * such as add, delete, update and display are performed.
 * </p>
 *
 * @author  Pavithra S
 */
@Controller
@RequestMapping("CategoryController")
public class CategoryController {
    private CategoryService categoryService = new CategoryServiceImpl(); 
    private DVDStoreLogger logger = new DVDStoreLogger();               
    
    /**
     * <p>
     * The addForm method displays the form to add new Category
     * </p>
     *
     * @return  Returns the empty Category object to the addCategory view
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)  
    public ModelAndView addForm(){  
        return new ModelAndView(Constants.JSP_ADD_CATEGORY,"command",new Category());  
    }  
    
    /**
     * <p>
     * The addCategory method gets the input such as Category Name from the user
     * and stores the Category obtained to the database. 
     * </p>
     *
     * @param   category   Contains the Category details specified by Customer
     * @return  Returns success message if the Category is successfully inserted.
     *          Otherwise, returns failure message to the displayCategories view.
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST) 
    private ModelAndView addCategory(@ModelAttribute("category") 
            Category category) {
        ModelAndView modelAndView = new ModelAndView();
        Set<Category> categories = new LinkedHashSet<Category>();
        try {
            category.setStatus(Boolean.TRUE);
            if (categoryService.insertCategory(category)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_INSERT_CATEGORY_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CATEGORY_EXISTS);  
            }
            categories = getCategories(Boolean.TRUE);
            modelAndView.addObject(Constants.LABEL_CATEGORIES, categories);
            modelAndView.setViewName(Constants.JSP_DISPLAY_CATEGORIES); 
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /** 
     * <p> 
     * The deleteCategory method is used to remove a specific Category by ID
     * obtained from the user.  
     * </p>
     *
     * @param   id  ID of the Category to be deleted
     * @return  Returns success message if the Category is successfully deleted.
     *          Otherwise, returns failure message to the displayCategories view.
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST) 
    private ModelAndView deleteCategory(@RequestParam("id")Integer id) {
        Set<Category> categories = new LinkedHashSet<Category>();
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (categoryService.deleteCategory(id)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CATEGORY_DELETE_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CATEGORY_DELETE_FAILURE);
            }
            categories = getCategories(Boolean.TRUE);
            modelAndView.addObject(Constants.LABEL_CATEGORIES, categories);
            modelAndView.setViewName(Constants.JSP_DISPLAY_CATEGORIES);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p>
     * The displayCategories method is used to display details of all the  
     * categories available.
     * </p>
     *
     * @return  Returns all the available Categories. Otherwise, returns failure 
     *          message to the displayCategories view. 
     */ 
    @RequestMapping(value = "display", method = RequestMethod.POST)
    private ModelAndView displayCategories() {
        Set<Category> categories = new LinkedHashSet<Category>();
        categories = getCategories(Boolean.TRUE);
        if (!categories.isEmpty()) {
            return new ModelAndView(Constants.JSP_DISPLAY_CATEGORIES,
                Constants.LABEL_CATEGORIES,categories);  
        } else {
            return new ModelAndView(Constants.JSP_DISPLAY_CATEGORIES,
                Constants.LABEL_ALERT_MESSAGE,Constants.MESSAGE_EMPTY_CATEGORY);
        }
    }
    
    /**
     * <p>
     * The editForm method displays the form to change the Category details 
     * which contains the Category details to be altered.
     * </p>
     *
     * @param   id  ID of the Category to be altered
     * @return  Returns the Category object for the ID specified by the Customer  
     *          to the addCategory view
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    private ModelAndView editForm(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Category category = getCategory(id);
        modelAndView.addObject(Constants.LABEL_CATEGORY, category);
        modelAndView.addObject("command", new Category());
        modelAndView.setViewName(Constants.JSP_ADD_CATEGORY);
        return modelAndView;
    }
    
    /**
     * <p>
     * The updateCategory method is used to alter the Name of the Category for
     * the specified Category ID in the database.
     * </p>
     *
     * @param   category      Contains the Category details to be updated
     * @return  Returns success message if the Category is successfully updated.
     *          Otherwise, returns failure message to the displayCategories view.
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    private ModelAndView updateCategory(@ModelAttribute("category") 
            Category category) {
        ModelAndView modelAndView = new ModelAndView();
        Set<Category> categories = new LinkedHashSet<Category>();
        try {
            category.setStatus(Boolean.TRUE);
            if (categoryService.updateCategory(category)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CATEGORY_UPDATE_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CATEGORY_EXISTS);
            }
            categories = getCategories(Boolean.TRUE);
            modelAndView.addObject(Constants.LABEL_CATEGORIES, categories);
            modelAndView.setViewName(Constants.JSP_DISPLAY_CATEGORIES);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        } 
        return modelAndView;
    }
    
    /**
     * <p>
     * The getDeletedCategories method is used to display details of all the  
     * deleted categories available.
     * </p>
     *
     * @return  Returns all the deleted Categories to the restoreCategory view.
     *          Otherwise, returns failure message to the displayCategories view.
     */
    @RequestMapping(value = "getDeletedCategories", method = RequestMethod.POST)
    private ModelAndView getDeletedCategories() {
        ModelAndView modelAndView = new ModelAndView();
        Set<Category> categories = new LinkedHashSet<Category>();
        categories = getCategories(Boolean.FALSE);
        if (!categories.isEmpty()) {
            return new ModelAndView(Constants.JSP_RESTORE_CATEGORY, 
                Constants.LABEL_CATEGORIES, categories); 
        } 
        categories = getCategories(Boolean.TRUE);
        modelAndView.addObject(Constants.LABEL_CATEGORIES, categories);
        modelAndView.setViewName(Constants.JSP_DISPLAY_CATEGORIES);
        return modelAndView;
    }
    
    /**
     * <p>
     * Used to restore the deleted categories based on Category ID obtained 
     * </p>
     *
     * @param   id  ID of the Category to be restored
     * @return  Returns success message if the Category is successfully restored.
     *          Otherwise, returns failure message to the displayCategories view.
     */
    @RequestMapping(value = "restore", method = RequestMethod.POST)
    private ModelAndView restoreCategory(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Set<Category> categories = new LinkedHashSet<Category>();
        try {
            if (categoryService.restoreCategory(id)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CATEGORY_RESTORE_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CATEGORY_RESTORE_FAILURE);
            }
            categories = getCategories(Boolean.TRUE);
            modelAndView.addObject(Constants.LABEL_CATEGORIES, categories);
            modelAndView.setViewName(Constants.JSP_DISPLAY_CATEGORIES);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p>
     * The displayDvds method is used to display the details of all the  
     * DVDs available for a specific category.
     * </p>
     *
     * @param   id  ID of the Category for which the DVDs ae to be displayed
     * @return  Returns all the available DVDs to the displayDvds view.
     *          Otherwise, returns failure message to the displayCategories view.
     */ 
    @RequestMapping(value = "displayDvds", method = RequestMethod.POST)
    private ModelAndView displayDvds(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        Set<Category> categories = new LinkedHashSet<Category>();

        Category category = getCategory(id);
        dvds = category.getDvds();
        if (!dvds.isEmpty()) {
            modelAndView.addObject(Constants.LABEL_DVDS, dvds);
            modelAndView.setViewName(Constants.JSP_DISPLAY_DVDS);
        } else { 
            modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                Constants.MESSAGE_EMPTY_DVD_STORE);
            categories = getCategories(Boolean.TRUE);
            modelAndView.addObject(Constants.LABEL_CATEGORIES, categories);
            modelAndView.setViewName(Constants.JSP_DISPLAY_CATEGORIES);
        } 
        return modelAndView;
    }
    
    /**
     * <p>
     * Used to retrieve all the available Categories 
     * </p>
     *
     * @param  status  Status of the Category checking whether it has been 
     *                 deleted or not
     * @return  Returns all the available Categories
     */
    private Set<Category> getCategories(Boolean status) {
        Set<Category> categories = new LinkedHashSet<Category>();
        try {
            categories = categoryService.getCategories(status);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return categories;
    }
    
    /**
     * <p>
     * Used to retrieve the Category for the specified ID 
     * </p>
     *
     * @param   id  ID of the Category to be retrieved
     * @return  Returns the Category for the specified ID
     */
    private Category getCategory(Integer id) {
        Category category = new Category();
        try {
            category = categoryService.retrieveById(id,Boolean.TRUE);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return category;
    }
}
