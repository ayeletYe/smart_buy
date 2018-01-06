package com.example.yaeli.smart_buy;

/**
 * Created by yaeli on 30/11/2017.
 */

public class User {
    String firstName, lastName, userName, address,city,Email;
    //public static boolean permissionIsGranted=false;

    public User(String Email, String firstName,String lastName, String address, String city){
        //this.userName=userName;
        //this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.city=city;
        this.Email=Email;
    }

    public User(String userName, String firstName,String lastName, String city){
        this.userName=userName;
        //this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.city=city;
        //this.Email=Email;
    }










}
