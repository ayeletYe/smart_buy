package com.example.yaeli.smart_buy;

/**
 * Created by yaeli on 30/11/2017.
 */

public class User {
    String firstName, lastName, userName, address,city,Email;
    boolean isAdmin;
    boolean photo;


    public User(boolean isAdmin,String Email, String userName,String firstName,String lastName, String address, String city){
        this.userName=userName;
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.city=city;
        this.Email=Email;
        this.isAdmin=isAdmin;
        photo=false;
    }

//    public User(boolean isAdmin,String Email,String userName, String firstName,String lastName, String city){
//        this.userName=userName;
//        this.firstName=firstName;
//        this.lastName=lastName;
//        this.city=city;
//        this.Email=Email;
//        this.isAdmin=isAdmin;
//    }










}
