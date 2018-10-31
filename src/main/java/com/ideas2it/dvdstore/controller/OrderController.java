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
import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.DVD;
import com.ideas2it.dvdstore.model.Order;
import com.ideas2it.dvdstore.service.OrderService;
import com.ideas2it.dvdstore.service.impl.OrderServiceImpl;

/**
 * <p>
 * The OrderController gets the inputs from the user on which the operations 
 * such as add and delete are performed.
 * </p>
 *
 * @author  Pavithra S
 */
@Controller
@RequestMapping("OrderController")
public class OrderController {
    private OrderService orderService = new OrderServiceImpl();
    private DVDStoreLogger logger = new DVDStoreLogger();               
    
    /**
     * <p>
     * Used to display details of all the Orders available for the Customer ID 
     * specified.
     * </p>
     *
     * @param  request     Request sent by the User
     * @return Returns the set of Orders placed by the Customer to the 
     *         displayOrders view. Otherwise, returns failure message to the
     *         displayCustomer view.
     */
    @RequestMapping(value = "display", method = RequestMethod.POST)
    private ModelAndView displayOrders(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        Integer customerId = (Integer)session.getAttribute("id");
        Set<Order> orders = new LinkedHashSet<Order>();
        orders = getOrders(customerId);
        if (!orders.isEmpty()) {
            return new ModelAndView
                ("displayOrders", Constants.LABEL_ORDERS, orders);  
        } else {
            Customer customer = getCustomer(customerId);
            modelAndView.addObject("customer", customer);
            modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_ORDERS_DISPLAY_FAILURE);
            modelAndView.setViewName("displayCustomer");
            return modelAndView;
        }
    }
    
    /**
     * <p>
     * The addForm method displays the form to add new Order which contains the
     * details of all the available DVDs, fields to specify the Quantity 
     * required , date of Delivery and the Addresses of the Customer.
     * </p>
     *
     * @param   request  Request sent by the User
     * @return  Returns the empty Order object along with the available DVDs 
     *          and Addresses to the addOrder view
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)  
    public ModelAndView addForm(HttpServletRequest request){  
        ModelAndView modelAndView = new ModelAndView();
        Set<DVD> dvds = new LinkedHashSet<DVD>();
        List<Address> addresses = new ArrayList<Address>();
        try {
            HttpSession session = request.getSession(false);
            Integer customerId = (Integer)session.getAttribute("id");
            dvds = orderService.getDVDs(Boolean.TRUE);
            Customer customer = orderService.retrieveByCustomerId(customerId);
            addresses = customer.getAddresses();
            if (!dvds.isEmpty() && !addresses.isEmpty()) {
                modelAndView.addObject(Constants.LABEL_DVDS, dvds);
                modelAndView.addObject(Constants.LABEL_ADDRESSES, addresses);
                modelAndView.addObject("command", new Order());
                modelAndView.setViewName("addOrder");
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_DVD_OR_ADDRESS_NOT_AVAILABLE);
                modelAndView.addObject("customer", customer);
                modelAndView.setViewName("displayCustomer");
            }
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;  
    }
    
    /**
     * <p>
     * The addOrder method gets the input such as select a specific DVD from the 
     * List, Quantity, Delivery Date and Address from the user and stores the  
     * details of the new Order to the database.
     * </p> 
     *
     * @param   request  Request sent by the User
     * @param   order    Contains the Order details specified by the Customer
     * @return  Returns success message if the Order is successfully placed.
     *          Otherwise, returns failure message to the displayOrders view.
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST) 
    private ModelAndView purchaseDVD(@ModelAttribute("order") Order order,
            HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        Integer customerId = (Integer)session.getAttribute("id");
        Set<Order> orders = new LinkedHashSet<Order>();
        try {
            DVD dvd = getDvd(request);
            Address address = getAddress(request);
            order.setDvd(dvd);
            order.setAddress(address);
            order.setPrice(dvd.getPrice());
            order.setCustomerId(customerId);
            if (orderService.insertOrder(order)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_ORDER_INSERT_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_ORDER_INSERT_FAILURE);  
            }
            orders = getOrders(customerId);
            modelAndView.addObject(Constants.LABEL_ORDERS, orders);
            modelAndView.setViewName("displayOrders");
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }
    
    /**
     * <p> 
     * Used to cancel the Order based on the Order ID obtained from the Customer 
     * </p>
     *
     * @param   id          ID of the Order to be deleted
     * @param   customerId  ID of the Customer whose Order is to be deleted
     * @return  Returns success message if the Order is successfully deleted.
     *          Otherwise, returns failure message to the displayOrders view.
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST) 
    private ModelAndView cancelOrder(@RequestParam("id")Integer id,
            @RequestParam("customerId")Integer customerId) {
        Set<Order> orders = new LinkedHashSet<Order>();
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (orderService.deleteOrder(id)) {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_ORDER_DELETE_SUCCESS);
            } else {
                modelAndView.addObject(Constants.LABEL_ALERT_MESSAGE,
                    Constants.MESSAGE_ORDER_DELETE_FAILURE);
            }
            orders = getOrders(customerId);
            modelAndView.addObject(Constants.LABEL_ORDERS, orders);
            modelAndView.setViewName("displayOrders");
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
     * @param  customerId  ID for which the Orders are to be obtained
     * @return Returns all the Orders placed by the Customer. Otherwise, returns 
     *         failure message to the displayCustomer view. 
     */
    @RequestMapping(value = "orders", method = RequestMethod.POST)
    private ModelAndView displayCustomerOrders(@RequestParam("id")
            Integer customerId) {
        Set<Order> orders = new LinkedHashSet<Order>();
        orders = getOrders(customerId);
        if (!orders.isEmpty()) {
            return new ModelAndView
                ("displayOrders", Constants.LABEL_ORDERS, orders);  
        } else {
            Customer customer = getCustomer(customerId);
            return new ModelAndView("displayCustomer",
                Constants.LABEL_ALERT_MESSAGE,
                Constants.MESSAGE_ORDERS_DISPLAY_FAILURE);
        }
    }
    
    /**
     * <p>
     * Used to retrieve details of all the Orders available for the Customer ID 
     * specified.
     * </p>
     *
     * @param  customerId  ID for which the Orders are to be obtained
     * @return Returns the set of Orders placed for the Customer ID specified
     */
    private Set<Order> getOrders(Integer customerId) {
        Set<Order> orders = new LinkedHashSet<Order>();
        try {
            orders = orderService.getOrders(customerId);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return orders;
    }
    
    /**
     * <p>
     * Used to retrieve the details of the Customer for the ID specified
     * </p>
     * 
     * @param   id       ID of the Customer
     * @return  Returns the Customer for the specified ID
     */
    private Customer getCustomer(Integer id) {
        Customer customer = new Customer();
        try {
            customer = orderService.retrieveByCustomerId(id);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return customer;
    }
    
    /**
     * <p>
     * Used to get the details of the DVD selected by the Customer
     * </p>
     * 
     * @param   request  Request sent by the User
     * @return  Returns the DVD selected by the Customer
     */
    private DVD getDvd(HttpServletRequest request) {
        DVD dvd = new DVD();
        try {
            Integer dvdId = Integer.parseInt(request
                .getParameter("selectedDvd"));
            dvd = orderService.retrieveByDvdId(dvdId);
        } catch (DVDException e) {
            logger.error(e.getMessage());
        }
        return dvd;
    }
    
    /**
     * <p>
     * Used to get the details of the Address selected by the Customer
     * </p>
     * 
     * @param   request  Request sent by the User
     * @return  Returns the Address selected by the Customer
     */
    private Address getAddress(HttpServletRequest request) {
        Address address = new Address();
        Integer addressId = Integer.parseInt(request
            .getParameter("selectedAddress"));
        address.setId(addressId);
        return address;
    }
}
