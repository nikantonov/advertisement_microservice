package com.ms3.dse;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * This is a usersearch Entity for Hibernate.
 * Here i save or read the information about users and their latest search criteria and date of this.
 * I get users from MS1, and form usersearch by myself
 */
@Entity
public class Usersearch {

    @Id
    private Long userid;

    private String name;
    private String surname;
    private String latestsearch;
    private java.sql.Date datum;

    public Usersearch() {}

    public Usersearch(User a) {
        this.userid = a.getId();
        this.name = a.getFirstName();
        this.surname = a.getLastName();
        this.latestsearch = null;
        this.datum = null;
    }

    public Long getUserid() {
        return userid;
    }

    public String getName(){
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLatestsearch() {
        return latestsearch;
    }

    public void setLatestsearch(String latestsearch) {
        this.latestsearch = latestsearch;
    }

    public java.sql.Date getDatum() {
        return datum;
    }

    public void setDatum(java.sql.Date datum) {
        this.datum = datum;
    }

}
