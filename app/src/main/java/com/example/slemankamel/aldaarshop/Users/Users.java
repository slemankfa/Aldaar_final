package com.example.slemankamel.aldaarshop.Users;

/**
 * Created by SlemanKamel on 10/18/2017.
 */

public abstract class Users {

    private String id;

    private String name;

    private  String email ;

    private  String  picture ;


    private  String Gender ;


    public Users() {
    }

    public Users(String id, String name, String email, String picture, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        Gender = gender;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if( name != null)
        {
            this.name = name;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.contains("@"))
        {
            this.email = email;
        }
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
