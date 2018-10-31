package com.ideas2it.dvdstore.controller;

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
import com.ideas2it.dvdstore.service.impl.AddressServiceImpl;
import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.service.AddressService;

/**
 * <p>
 * The AddressController gets the inputs from the user on which the operations 
 * such as add and delete are performed.
 * </p>
 *
 * @author  Pavithra S
 */
@Controller
@RequestMapping("AddressController")
public class AddressController {
    private AddressService addressService = new AddressServiceImpl();
    private DVDStoreLogger logger = new DVDStoreLogger(); 
    
    /**
     * <p> 
     * Used to delete the Address based on the ID obtained from the user.
     * </p>
     *
     * @param   id          ID of the Address wich is to be deleted
     * @param   customerId  ID of the Customer whose Address is to be deleted
     * @return  Returns success message if the Address is successfully deleted.
     *          Otherwise, returns failure message to the addCustomer view.
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST) 
    private ModelAndView deleteAddress(@RequestParam("addressId")Integer id,
            @RequestParam(Constants.LABEL_ID)Integer customerId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (addressService.deleteAddress(id, customerId)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_ADDRESS_DELETE_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    "Order has been placed, cannot be Deleted");
            }
            Customer customer = getCustomer(customerId);
            modelAndView.addObject(Constants.LABEL_CUSTOMER, customer);
            modelAndView.setViewName(Constants.JSP_ADD_CUSTOMER);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p>
     * The addForm method displays the form to add new Address
     * </p>
     *
     * @return  Returns the empty Address object to the addAddress view
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)  
    public ModelAndView addForm(){  
        return new ModelAndView(Constants.JSP_ADD_ADDRESS,"command",new Address());  
    }
    
    /**
     * <p>
     * The addAddress method gets the input such as Street, City, Zipcode and   
     * State from the customer and stores the Address obtained to the database.
     * </p> 
     *
     * @param   request  Request sent by the User
     * @param   address  Contains the Address details specified by the Customer
     * @return  Returns success message if the Address is successfully inserted.
     *          Otherwise, returns failure message to the displayCustomer view.
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST) 
    private ModelAndView addAddress(@ModelAttribute("address") Address address,
            HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            HttpSession session = request.getSession(false);
            Integer customerId = (Integer)session.getAttribute(Constants.LABEL_ID);
            address.setCustomerId(customerId);
            if (addressService.insertAddress(address)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_ADDRESS_INSERT_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_ADDRESS_INSERT_FAILURE);  
            }
            Customer customer = getCustomer(customerId);
            modelAndView.addObject(Constants.LABEL_CUSTOMER, customer);
            modelAndView.setViewName(Constants.JSP_DISPLAY_CUSTOMER); 
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p>
     * Used to get the details for the Customer ID specified
     * </p>
     * 
     * @param   id  ID of the Customer
     * @return  Returns the Customer details obtained for the specified ID.
     *          Otherwise, returns empty Customer Object.
     */
    private Customer getCustomer(Integer id) {
        Customer customer = new Customer();
        try {
            customer = addressService.retrieveByCustomerId(id);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return customer;
    }
}
