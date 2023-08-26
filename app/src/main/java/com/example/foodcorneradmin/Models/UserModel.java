package com.example.foodcorneradmin.Models;




public class UserModel {
    private String Name,Email,MobileNo,Restaurant;

    public UserModel(String name, String email,String restaurant) {
        Name = name;
        Email = email;
        Restaurant = restaurant;
    }

    public UserModel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(String restaurant) {
        Restaurant = restaurant;
    }
}


