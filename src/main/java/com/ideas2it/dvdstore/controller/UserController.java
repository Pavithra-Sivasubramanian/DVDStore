package com.ideas2it.dvdstore.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.servlet.ModelAndView; 

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.exception.DVDException;
import com.ideas2it.dvdstore.log.DVDStoreLogger;
import com.ideas2it.dvdstore.filter.AuthorizationFilter;
import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.service.UserService;
import com.ideas2it.dvdstore.service.impl.UserServiceImpl;

/**
 * <p>
 * The UserController gets the inputs from the user on which the operations 
 * such as login and logout are performed. Also used to create a new Account 
 * by Adding a new Customer
 * </p>
 *
 * @author  Pavithra S
 */
@Controller
public class UserController {
    private UserService userService = new UserServiceImpl();
    private DVDStoreLogger logger = new DVDStoreLogger();
    
    /**
     * <p>
     * Used to login to an existing Account based on the details such as Username 
     * and password obtained from the user. Identifies whether it is the Admin
     * or the Customer that logged in and redirects the page accordingly.
     * </p>
     *
     * @param   request  Request sent by the User
     * @param   email    Email ID entered by the User
     * @param   email    Password entered by the User
     * @return  Displays the displayDvds view containing all the DVDs if 
     *          the Admin has logged in. Displays the displayCustomer view 
     *          containing the logged in Customer details. Returns failure 
     *          message, if the user enters an Invalid Account 
     *          
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    private ModelAndView userLogin(HttpServletRequest request,
            @RequestParam("email")String email,
            @RequestParam("password")String password) {
        ModelAndView modelAndView = new ModelAndView("userLogin",
            Constants.LABEL_ALERT_MESSAGE,"ERROR OCCURED");
        try {
            User user = userService.retrieveByEmailAndPassword
                (email, password, Boolean.TRUE);
            HttpSession session = request.getSession(true);
            if (null != user) {
                String role = user.getRole();
                Integer userId = user.getId();
                if (role.equals(Constants.LABEL_CUSTOMER)) {
                    session.setAttribute(Constants.LABEL_ROLE, role);
                    session.setAttribute(Constants.LABEL_USER_ID, userId);
                    modelAndView = displayCustomer(request, email, password);
                } else if (role.equals(Constants.LABEL_ADMIN)) {
                    session.setAttribute(Constants.LABEL_ROLE, role);
                    session.setAttribute(Constants.LABEL_USER_ID, userId);
                    modelAndView = displayDvds();
                }
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_INVALID_USERNAME_OR_PASSWORD);
                modelAndView.setViewName("userLogin");
            }
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }  
        return modelAndView;  
    }
    
    /**
     * <p>
     * Used to display the details of Customer based on the Email ID and 
     * password obtained from the user
     * </p>
     * 
     * @param   request  Request sent by the User
     * @param   email    Email ID entered by the Customer
     * @param   email    Password entered by the Customer
     * @return  Displays the displayCustomer view containing the logged in 
     *          Customer details. Returns failure message, if the user 
     *          enters an Invalid Account 
     */
    private ModelAndView displayCustomer(HttpServletRequest request, 
            String email, String password) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Customer customer = userService.retrieveCustomer
                (email, password, Boolean.TRUE);
            HttpSession session = request.getSession(true);
            if (null != customer) {
                Integer id = customer.getId();
                session.setAttribute(Constants.LABEL_ID, id);
                modelAndView.addObject(Constants.LABEL_CUSTOMER, customer);
                modelAndView.setViewName("displayCustomer");
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_INVALID_USERNAME_OR_PASSWORD);
                modelAndView.setViewName("userLogin");
            }
        } catch (DVDException e) {
            logger.error(e.getMessage());
        } 
        return modelAndView;
    }
    
    /**
     * <p>
     * The displayDvds method is used to display details of all the DVDs 
     * available.
     * </p>
     *
     * @return  Displays the displayDvds view containing all the DVDs if 
     *          the Admin has logged in.
     */
    private ModelAndView displayDvds() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Set<DVD> dvds = userService.getDvds(Boolean.TRUE);
            modelAndView.addObject(Constants.LABEL_DVDS,dvds);
            modelAndView.setViewName("displayDvds");
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /** 
     * <p>
     * Used to logout the user Account by closing the existing Session 
     * </p>
     *
     * @param   request  Request sent by the User
     * @return  Invalidates the session and returns the Success message to the
     *          userLogin view
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    private ModelAndView userLogout(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        if (null != session) {
            session.invalidate();
        }
        return new ModelAndView("userLogin", Constants.LABEL_ALERT_MESSAGE, 
            Constants.MESSAGE_LOGOUT_SUCCESS);
    }
    
    /**
     * <p>
     * The addForm method displays the form to add new Customer
     * </p>
     *
     * @return  Returns the empty Customer object to the addCustomer view
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)  
    public ModelAndView addForm(){  
        return new ModelAndView("addCustomer", "command", new Customer());  
    }
    
    /**
     * <p>
     * The addCustomer method gets the input such as Customer Name, Password,   
     * Contact Number, Email and Address from the user and stores the details of 
     * the Customer obtained to the database.
     * </p> 
     *
     * @param   request   Request sent by the User
     * @param   customer  Contains the details specified by the Customer
     * @return  Returns success message if the Account is successfully created.
     *          Otherwise, returns failure message to the userLogin view.
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST) 
    private ModelAndView addCustomer(@ModelAttribute("customer") 
            Customer customer, HttpServletRequest request) {
        User user = new User();
        ModelAndView modelAndView = new ModelAndView();
        List<Address> addresses = new ArrayList<Address>();
        try {
            addresses = customer.getAddresses();
            user.setUserName(customer.getEmail());
            user.setPassword(customer.getPassword());
            user.setRole(Constants.LABEL_CUSTOMER);
            user.setStatus(Boolean.TRUE);
            
            customer.setAddresses(addresses);
            customer.setUser(user);
            customer.setStatus(Boolean.TRUE);
            if (userService.insertCustomer(customer)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CUSTOMER_INSERT_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CUSTOMER_EXISTS);  
            }
            modelAndView.setViewName("userLogin"); 
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /** 
     * <p>
     * Used to redirect to the userLogin view when invoked
     * </p>
     *
     * @return  Return to the User Login page
     */    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    private String loginForm() {
        return "userLogin";
    }
}
