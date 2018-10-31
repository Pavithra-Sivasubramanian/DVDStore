package com.ideas2it.dvdstore.controller;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

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
import com.ideas2it.dvdstore.service.DVDService;
import com.ideas2it.dvdstore.service.impl.DVDServiceImpl;

/**
 * <p>
 * The DVDController gets the inputs from the user on which the operations 
 * such as add, update, delete and display are performed.
 * </p>
 *
 * @author  Pavithra S
 */
@Controller
@RequestMapping("DvdController")
public class DVDController {
    private DVDService dvdService = new DVDServiceImpl(); 
    private DVDStoreLogger logger = new DVDStoreLogger();               
    
    /**
     * <p>
     * The displayDvds method is used to display details of all the DVDs 
     * available.
     * </p>
     *
     * @return  Returns all the available DVDs to the displayDvds view.
     *          Otherwise, returns failure message to the addDvd view.
     */
    @RequestMapping(value = "display", method = RequestMethod.POST)
    private ModelAndView displayDvds() {
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        dvds = getDvds(Boolean.TRUE);
        if (!dvds.isEmpty()) {
            return new ModelAndView("displayDvds", Constants.LABEL_DVDS, dvds);  
        } else {
            return new ModelAndView("addDvd",Constants.LABEL_ALERT_MESSAGE,
                Constants.MESSAGE_EMPTY_DVD_STORE);
        }
    }
    
    /**
     * <p>
     * The addForm method displays the form along with all available Categories 
     * to add new DVD
     * </p>
     *
     * @return  Returns the empty DVD object along with the available Categories 
     *          to the addDvd view
     */
    @RequestMapping(value = "add", method = RequestMethod.POST) 
    private ModelAndView addForm() {
        ModelAndView modelAndView = new ModelAndView();
        Set<Category> categories = new LinkedHashSet<Category>();
        try {
            categories = dvdService.getCategories(Boolean.TRUE);
            if (!categories.isEmpty()) {
                modelAndView.addObject("command", new DVD());
                modelAndView.addObject(Constants.LABEL_CATEGORIES, categories);
                modelAndView.setViewName("addDvd");
                return modelAndView;
            }
        } catch (DVDException e) {
            logger.error(e.getMessage());
        } 
        return new ModelAndView("displayDvds",Constants.LABEL_ALERT_MESSAGE,
            Constants.MESSAGE_EMPTY_CATEGORY);
    }
    
    /**
     * <p>
     * The addDVD method gets the input such as Name, Rating, Release date  
     * and Category list from the user and stores the details of the new DVD to  
     * the database.
     * </p> 
     *
     * @param   request  Request sent by the User
     * @param   dvd      Contains the DVD details specified by the user
     * @return  Returns success message if the DVD is successfully inserted.
     *          Otherwise, returns failure message to the displayDvds view.
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    private ModelAndView addDVD(@ModelAttribute("dvd")DVD dvd, 
            HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        Set<Category> categories = new LinkedHashSet<Category>();
        try {
            categories = getCategories(request);
            dvd.setGenre(categories);
            dvd.setStatus(Boolean.TRUE);
            if (dvdService.insertDVD(dvd)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_DVD_INSERT_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_DVD_EXISTS);
            }   
            dvds = getDvds(Boolean.TRUE);
            modelAndView.addObject("dvds", dvds);
            modelAndView.setViewName("displayDvds"); 
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p> 
     * The deleteDVD method is used to remove the details of a specific DVD
     * based on the DVD ID obtained from the user.  
     * </p>
     *
     * @param   id  ID of the DVD to be deleted
     * @return  Returns success message if the DVD is successfully deleted.
     *          Otherwise, returns failure message to the displayDvds view.
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST) 
    private ModelAndView deleteDVD(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        try {
            if (dvdService.deleteDVD(id)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_DVD_DELETE_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_DVD_DELETE_FAILURE);
            }
            dvds = getDvds(Boolean.TRUE);
            modelAndView.addObject("dvds", dvds);
            modelAndView.setViewName("displayDvds");
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p>
     * Used to retrieve the details of the DVDs for the DVD name specified
     * </p>
     *
     * @param   name  Name of the DVD to be Retrieved
     * @return  Returns the DVD details for the DVD name specified
     *          Otherwise, returns failure message to the displayDvds view.
     */
    @RequestMapping(value = "search", method = RequestMethod.POST) 
    private ModelAndView searchDVD(@RequestParam("name")String name) {
        ModelAndView modelAndView = new ModelAndView();
        Set<DVD> existingDvds = new LinkedHashSet<DVD>();
        try {
            Set<DVD> dvds = dvdService.retrieveByName(name);
            if (!dvds.isEmpty()) {
                modelAndView.addObject(Constants.LABEL_DVDS, dvds);
            } else {
                existingDvds = getDvds(Boolean.TRUE);
                modelAndView.addObject(Constants.LABEL_DVDS, existingDvds);
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_DVD_NOT_AVAILABLE);
            }
            modelAndView.setViewName("displayDvds");
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p>
     * The editForm method displays the form to change the DVD details which 
     * contains the DVD details to be altered.
     * </p>
     *
     * @param   id  ID of the DVD to be altered
     * @return  Returns the DVD object along with the Categories specified for 
     *          the DVD to the addDvd view
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    private ModelAndView editForm(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Set<Category> categories = new LinkedHashSet<Category>();
        try {
            DVD existingDvd = dvdService.retrieveById(id);
            modelAndView.addObject(Constants.LABEL_DVD, existingDvd);
            categories = dvdService.getCategories(Boolean.TRUE);
            modelAndView.addObject(Constants.LABEL_CATEGORIES,categories);
            modelAndView.addObject("command", new DVD());
            modelAndView.setViewName("addDvd");
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }  
        return modelAndView;
    }
    
    /**
     * <p>
     * The updateDVD method is used to alter the Name, Categories, language,  
     * Rating, Quantity, Price and Release date of the DVD 
     * </p>
     *
     * @param   request  Request sent by the User
     * @param   dvd      Contains the DVD details to be updated
     * @return  Returns success message if the DVD is successfully updated.
     *          Otherwise, returns failure message to the displayDvds view.
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    private ModelAndView updateDVD(@ModelAttribute("dvd")DVD dvd, 
            HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        Set<Category> categories = new LinkedHashSet<Category>();
        try {
            categories = getCategories(request);
            dvd.setGenre(categories);
            dvd.setStatus(Boolean.TRUE);
            if (dvdService.updateDVD(dvd)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_DVD_UPDATE_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_DVD_EXISTS);
            }
            dvds = getDvds(Boolean.TRUE);
            modelAndView.addObject("dvds", dvds);
            modelAndView.setViewName("displayDvds");
        } catch (DVDException e) {
            logger.error(e.getMessage());
        } 
        return modelAndView;
    }
    
    /**
     * <p>
     * Used to display the details of all the deleted DVDs available.  
     * </p>
     *
     * @return  Returns all the deleted DVDs to the restoreDvd view.
     *          Otherwise, returns failure message to the displayDvds view.
     */ 
    @RequestMapping(value = "getDeletedDvds", method = RequestMethod.POST) 
    private ModelAndView getDeletedDvds() {
        ModelAndView modelAndView = new ModelAndView();
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        dvds = getDvds(Boolean.FALSE);
        if (!dvds.isEmpty()) {
            return new ModelAndView("restoreDvd", Constants.LABEL_DVDS, dvds);  
        } else {
            modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                Constants.MESSAGE_EMPTY_DVD_STORE);
            dvds = getDvds(Boolean.TRUE);
            modelAndView.addObject("dvds", dvds);
            modelAndView.setViewName("displayDvds");
            return modelAndView;
        }
    }
    
    /**
     * <p>
     * Used to restore the details of a specific DVD based on ID obtained from
     * the user
     * </p>
     *
     * @param   id  ID of the DVD to be restored
     * @return  Returns success message if the DVD is successfully restored.
     *          Otherwise, returns failure message to the displayDvds view.
     */
    @RequestMapping(value = "restore", method = RequestMethod.POST)
    private ModelAndView restoreCategory(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        try {
            if (dvdService.restoreDVD(id)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_DVD_RESTORE_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_DVD_RESTORE_FAILURE);
            }
            dvds = getDvds(Boolean.TRUE);
            modelAndView.addObject("dvds", dvds);
            modelAndView.setViewName("displayDvds");
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p>
     * Used to displays all the Categories available to the user 
     * </p>
     *
     * @param   request  Request sent by the Customer
     * @return  Returns the Set of Categories chosen by the Customer
     */
    private Set<Category> getCategories(HttpServletRequest request) {
        Set<Category> categories = new LinkedHashSet<Category>();
        String[] categoryIds = request.getParameterValues
            (Constants.LABEL_CATEGORY);
        for (String id : categoryIds) {
            Category category = new Category();
            Integer categoryId = Integer.parseInt(id);
            category.setId(categoryId);
            categories.add(category);
        }
        return categories;
    }
    
    /**
     * <p>
     * Used to retrieve all the available DVDs 
     * </p>
     *
     * @param   status  Status of the DVD checking whether it is deleted or not
     * @return  Returns all the available DVDs
     */
    private Set<DVD> getDvds(Boolean status) {
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        try {
            dvds = dvdService.getDVDs(status);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return dvds;
    }
}
