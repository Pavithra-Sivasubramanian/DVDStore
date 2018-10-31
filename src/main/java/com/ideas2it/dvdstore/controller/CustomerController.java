package com.ideas2it.dvdstore.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.model.Order;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.service.CustomerService;
import com.ideas2it.dvdstore.service.impl.CustomerServiceImpl;

/**
 * <p>
 * The CustomerController gets the inputs from the user on which the operations 
 * such as add, delete, update and display are performed.
 * </p>
 *
 * @author  Pavithra S
 */
@Controller
@RequestMapping("CustomerController")
public class CustomerController {
    private CustomerService customerService = new CustomerServiceImpl();
    private DVDStoreLogger logger = new DVDStoreLogger();               
    
    /**
     * <p>
     * Used to display the details of all the available Customers
     * </p>
     * 
     * @return  Returns all the available Customers to the displayCustomers view.
     *          Otherwise, returns failure message to the displayDvds view.
     */
    @RequestMapping(value = "display", method = RequestMethod.POST)
    private ModelAndView displayCustomers() {
        ModelAndView modelAndView = new ModelAndView();
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        Set<Customer> customers = new LinkedHashSet<Customer>();
        customers = getCustomers(Boolean.TRUE);
        if (!customers.isEmpty()) {
            return new ModelAndView("displayCustomers", "customers", customers);  
        } else {
            dvds = getDvds();
            modelAndView.addObject("dvds", dvds);
            modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                Constants.MESSAGE_CUSTOMERS_NOT_AVAILABLE);
            modelAndView.setViewName("displayDvds");
            return modelAndView;
        }
    }
    
    /**
     * <p>
     * Used to display details of all the deactivated Customer Accounts
     * </p>
     *
     * @return  Returns all the deactivated accounts to the displayCustomers 
     *          view. Otherwise, returns failure message to the displayDvds view.
     */ 
    @RequestMapping(value = "deletedAccounts", method = RequestMethod.POST)
    private ModelAndView deletedAccounts() {
        ModelAndView modelAndView = new ModelAndView();
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        Set<Customer> customers = new LinkedHashSet<Customer>();
        customers = getCustomers(Boolean.FALSE);
        if (!customers.isEmpty()) {
            return new ModelAndView("displayCustomers", "customers", customers);  
        } else {
            dvds = getDvds();
            modelAndView.addObject("dvds", dvds);
            modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                Constants.MESSAGE_CUSTOMERS_NOT_AVAILABLE);
            modelAndView.setViewName("displayDvds");
            return modelAndView;
        }
    }
    
    /**
     * <p>
     * Used to display details of all Addresses available for the Customer ID 
     * specified.
     * </p>
     *
     * @param   id  ID of the Customer whose Addresses are to be displayed
     * @return  Returns the available Addresses of the Customer to the 
     *          displayAddresses view. Otherwise, returns failure message to the 
     *          displayCustomers view.
     */
    @RequestMapping(value = "addresses", method = RequestMethod.POST)
    private ModelAndView getAddresses(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        List<Address> addresses = new ArrayList<Address>();
        Set<Customer> customers = new LinkedHashSet<Customer>();
        try {
            Customer customer = customerService.retrieveById(id);
            addresses = customer.getAddresses();
            if (!addresses.isEmpty()) {
                modelAndView.addObject(Constants.LABEL_ADDRESSES, addresses);
                modelAndView.setViewName("displayAddresses");
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_ADDRESS_NOT_AVAILABLE);
                customers = getCustomers(Boolean.TRUE);
                modelAndView.addObject("customers", customers);
                modelAndView.setViewName("displayCustomers");
            }
        } catch (DVDException e) {
            logger.error(e.getMessage());
        } 
        return modelAndView;
    }
    
    /**
     * <p>
     * Used to display details of all the Orders available for the Customer ID 
     * specified.
     * </p>
     *
     * @param   id  ID of the Customer whose Orders are to be displayed
     * @return  Returns the available Orders of the Customer to the 
     *          displayOrders view. Otherwise, returns failure message to the 
     *          displayCustomers view.
     */
    @RequestMapping(value = "orders", method = RequestMethod.POST)
    private ModelAndView getOrders(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Set<Order> orders = new LinkedHashSet<Order>();
        Set<Customer> customers = new LinkedHashSet<Customer>();
        try {
            Customer customer = customerService.retrieveById(id);
            orders = customer.getOrders();
            if (!orders.isEmpty()) {
                modelAndView.addObject(Constants.LABEL_ORDERS, orders);
                modelAndView.setViewName("displayOrders");
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_ORDER_NOT_AVAILABLE);
                customers = getCustomers(Boolean.TRUE);
                modelAndView.addObject("customers", customers);
                modelAndView.setViewName("displayCustomers");
            }
        } catch (DVDException e) {
            logger.error(e.getMessage());
        } 
        return modelAndView;
    }
    
    /**
     * <p>
     * Used to retrieve the details of a particular Customer based on the 
     * Customer ID specified 
     * </p>
     *
     * @param   id  ID of the Customer details to be displayed
     * @return  Returns the details of the Customer to the displayCustomer
     *          view. Otherwise, returns failure message to the 
     *          displayCustomers view.
     */
    @RequestMapping(value = "search", method = RequestMethod.POST) 
    private ModelAndView searchCustomer(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Set<Customer> customers = new LinkedHashSet<Customer>();
        try {
            Customer customer = customerService.retrieveById(id);
            if (null != customer && customer.getStatus()) {
                modelAndView.addObject("customer", customer);
                modelAndView.setViewName("displayCustomer");
            } else {
                customers = getCustomers(Boolean.TRUE);
                modelAndView.addObject("customers", customers);
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CUSTOMER_NOT_AVAILABLE);
                modelAndView.setViewName("displayCustomers");
            }
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p>
     * The editForm method displays the form to change the Customer details
     * </p>
     *
     * @param   id  ID of the Customer details to be altered
     * @return  Returns the Customer object for the ID specified 
     *          to the addCustomer view
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    private ModelAndView editForm(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Customer customer = getCustomer(id);
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("command", new Customer());
        modelAndView.setViewName("addCustomer");
        return modelAndView;
    }
    
    /**
     * <p>
     * Used to alter the Name, Password, Contact Number, Email and Addresses 
     * of the Customer.
     * </p>
     *
     * @param  customer    Contains the updated Customer details
     * @return  Returns success message if the Customer is successfully updated.
     *          Otherwise, returns failure message to the displayCustomer view.
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    private ModelAndView updateCustomer(@ModelAttribute("customer")
            Customer customer) {
        User user = new User();
        ModelAndView modelAndView = new ModelAndView();
        Set<Customer> customers = new LinkedHashSet<Customer>();
        try {
            customer.setStatus(Boolean.TRUE);
            if (customerService.updateCustomer(customer)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CUSTOMER_UPDATE_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CUSTOMER_EXISTS);
            }
            modelAndView.addObject("customer", customer);
            modelAndView.setViewName("displayCustomer");
        } catch (DVDException e) {
            logger.error(e.getMessage());
        } 
        return modelAndView;
    }
    
    /**
     * <p> 
     * Used to deactivate the Customer account which means the Customer details 
     * will persist but it cant be further accessed by the Customer but the 
     * details can be viewed by the Customer
     * </p>
     *
     * @param   id  ID of the Customer to be deleted
     * @return  Returns success message if the Customer is successfully deleted.
     *          Otherwise, returns failure message to the userLogin view.
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST) 
    private ModelAndView deactivateAccount(@RequestParam("id")Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (customerService.deleteCustomer(id)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CUSTOMER_DELETE_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_CUSTOMER_DELETE_FAILURE);
            }
            modelAndView.setViewName("userLogin");
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p>
     * Used to retrieve the details of the Customer for the ID specified
     * </p>
     * 
     * @param   id  ID of the Customer to be displayed
     * @return  Returns the Customer details for the specified ID
     */
    private Customer getCustomer(Integer id) {
        Customer customer = new Customer();
        try {
            customer = customerService.retrieveById(id);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return customer;
    }
    
    /**
     * <p>
     * Used to retrieve all the available DVDs 
     * </p>
     *
     * @return  Returns all the available DVDs
     */
    private Set<DVD> getDvds() {
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        try {
            dvds = customerService.getDvds(Boolean.TRUE);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return dvds;
    }
    
    /**
     * <p>
     * Used to get the details of all the available Customers
     * </p>
     * 
     * @param  status  Status of the Customers checking whether it has been 
     *                 deleted or not
     * @return  Returns all the existing Customers
     */
    private Set<Customer> getCustomers(Boolean status) {
        Set<Customer> customers = new LinkedHashSet<Customer>();
        try {
            customers = customerService.getCustomers(status);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return customers;
    }
}
