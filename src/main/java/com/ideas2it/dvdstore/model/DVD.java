package com.ideas2it.dvdstore.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.model.Category;
import com.ideas2it.dvdstore.util.DateUtil;

/**
 * <p>
 * This contains the POJO class to get and set the ID, Name, Genre, Rating, 
 * Release Date, Status and the set of Categories of the DVD.
 * </p>
 *
 * @author  Pavithra S
 */
public class DVD {
    private Integer id;
    private String name;
    private String language;
    private Double rating;
    private Integer quantity;
    private Date releaseDate;
    private Double price;
    private Boolean status;
    private Set<Category> genre = new LinkedHashSet<Category>();

    public DVD() {
    }
    
    public DVD(String name, Set<Category> genre, String language,Double rating, 
            Integer quantity, Date releaseDate,Double price, Boolean status) {
        this.name = name;
        this.genre = genre;
        this.language = language;
        this.rating = rating;
        this.quantity = quantity;
        this.releaseDate = releaseDate;
        this.price = price;
        this.status = status;
    }

    public DVD(Integer id, String name, Set<Category> genre, String language,  
            Double rating, Integer quantity, Date releaseDate, Double price,  
            Boolean status) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.rating = rating;
        this.quantity = quantity;
        this.releaseDate = releaseDate;
        this.price = price;
        this.genre = genre;
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

    public void setGenre(Set<Category> genre) {
        this.genre = genre;
    }

    public Set<Category> getGenre() {
        return genre;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }
}
