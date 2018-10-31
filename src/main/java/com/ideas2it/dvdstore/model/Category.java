package com.ideas2it.dvdstore.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.ideas2it.dvdstore.common.Constants;

/**
 * <p>
 * This contains the POJO class to get and set the ID, Name, Status and set 
 * of DVDs of the Category.
 * </p>
 *
 * @author  Pavithra S 
 */
public class Category {
    private Integer id;
    private String name;
    private Boolean status;
    private Set<DVD> dvds = new LinkedHashSet<DVD>();
    
    public Category() {
    }
    
    public Category(String name, Boolean status) {
        this.name = name;
        this.status = status;
    } 

    public Category(Integer id, String name, Boolean status) {
        this.id = id;
        this.name = name;
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

    public void setDvds(Set<DVD> dvds) {
        this.dvds = dvds;
    }
    
    public Set<DVD> getDvds() {
        return dvds;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }
    
    /**
     * <p>
     * Returns the String representation of the Category object
     * </p>
     */           
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(); 
        stringBuilder.append(Constants.MESSAGE_ID).append(id)
            .append(Constants.MESSAGE_NAME).append(name);
        return stringBuilder.toString();               
    }

}
