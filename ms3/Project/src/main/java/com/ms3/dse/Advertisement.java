package com.ms3.dse;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 * This is an advertisement entity, similar to MS2
 */
@Entity
public class Advertisement {

    @Id
    private Long id;

    private String title;

    private java.sql.Date publishDate;

    private String description;

    private String email;

    private double price;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public java.sql.Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(java.sql.Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
       this.user = user;
    }

}
