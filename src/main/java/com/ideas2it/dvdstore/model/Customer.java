package com.ideas2it.dvdstore.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.model.Order;
import com.ideas2it.dvdstore.common.Constants;

/**
 * <p>
 * This contains the POJO class to get and set the ID, Name, Phone No, Address 
 * and set of Products of the Customer.
 * </p>
 *
 * @author  Pavithra S
 */
public class Customer {
    private Integer id;
    private String name;
    private String password;
    private String contactNo;
    private String email;
    private Boolean status;
    private List<Address> addresses = new ArrayList<Address>();
    private Set<Order> orders = new LinkedHashSet<Order>();
    private User user = new User();
    
    public Customer() {
    }
    
    public Customer(String name, String password, String contactNo, 
            String email, List<Address> addresses, User user, Boolean status) {
        this.name = name;
        this.password = password;
        this.contactNo = contactNo;
        this.email = email;
        this.addresses = addresses;
        this.user = user;
        this.status = status;
    }
    
    public Customer(Integer id, String name, String password, String contactNo, 
            String email, Boolean status) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.contactNo = contactNo;
        this.email = email;
        this.status = status;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
  
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getContactNo() {
        return contactNo;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }
    
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Address> getAddresses() {
        return addresses;
    }
    
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Order> getOrders() {
        return orders;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
    
    /**
     * <p>
     * Returns the String representation of the Customer object
     * </p>
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(); 
        stringBuilder.append(Constants.MESSAGE_CUSTOMER_ID).append(id)
            .append(Constants.MESSAGE_NAME).append(name)
            .append(Constants.MESSAGE_CONTACT_NO).append(contactNo)
            .append(Constants.MESSAGE_EMAIL_ADDRESS).append(email)
            .append(Constants.MESSAGE_ADDRESS).append(getAddresses());
        return stringBuilder.toString();               
    }
}
