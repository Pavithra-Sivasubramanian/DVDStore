package com.ideas2it.dvdstore.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.ideas2it.dvdstore.common.Constants;

/**
 * <p>
 * This contains the POJO class to get and set the ID, Quantity, Delivery date,
 * Price, Status and the DVD ordered. 
 * </p>
 *
 * @author  Pavithra S 
 */
public class Order {
    private Integer id;
    private Integer quantity;
    private Date deliveryDate;
    private Double price;
    private Integer customerId;
    private DVD dvd = new DVD();
    private Address address = new Address();
    
    public Order() {
    }
    
    public Order(DVD dvd, Integer quantity, Date deliveryDate, Double price,
            Address address) {
        this.dvd = dvd;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;
        this.price = price;
        this.address = address;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
 
    public Integer getId() {
        return id;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
 
    public Integer getQuantity() {
        return quantity;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    
    public void setDvd(DVD dvd) {
        this.dvd = dvd;
    }
    
    public DVD getDvd() {
        return dvd;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
    
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
 
    public Integer getCustomerId() {
        return customerId;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public Address getAddress() {
        return address;
    }
    
    /**
     * <p>
     * Returns the String representation of the Order object
     * </p>
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(); 
        stringBuilder.append(Constants.MESSAGE_ORDER_ID).append(id)
            .append(Constants.MESSAGE_DVDS).append(getDvd())
            .append(Constants.MESSAGE_QUANTITY_ORDERED).append(quantity)
            .append(Constants.MESSAGE_DELIVERY_DATE).append(deliveryDate)
            .append(Constants.MESSAGE_PRICE_ORDERED).append(price)
            .append("addresses").append(getAddress());
        return stringBuilder.toString();               
    }
}
