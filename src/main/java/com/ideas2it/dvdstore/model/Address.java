package com.ideas2it.dvdstore.model;

/**
 * <p>
 * This contains the POJO class to get and set the ID, Street, City, State and 
 * Zipcode of the Address
 * </p>
 *
 * @author  Pavithra S 
 */
public class Address{
    private Integer id;
    private String street;     
    private String city;     
    private String state;    
    private String zipcode;
    private Integer customerId;

    public Address() {}
   
    public Address(String street, String city, String state, String zipcode) {
        this.street = street; 
        this.city = city; 
        this.state = state; 
        this.zipcode = zipcode; 
        this.customerId = customerId;
    }
   
    public Integer getId() {
        return id;
    }
   
    public void setId(Integer id) {
        this.id = id;
    }
   
    public String getStreet() {
        return street;
    }
   
    public void setStreet(String street) {
        this.street = street;
    }
   
    public String getCity() {
        return city;
    }
   
    public void setCity(String city) {
        this.city = city;
    }
   
    public String getState() {
        return state;
    }
   
    public void setState(String state) {
        this.state = state;
    }
   
    public String getZipcode() {
        return zipcode;
    }
   
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
   
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
 
    public Integer getCustomerId() {
        return customerId;
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(); 
        stringBuilder.append("city").append(city)
            .append("street").append(street)
            .append("zipcode").append(zipcode)
            .append("state").append(state);
        return stringBuilder.toString();               
    }
}
