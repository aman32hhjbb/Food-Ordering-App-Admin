package com.example.foodcorneradmin.Models;

public class AddToCartModel {

    String Quantity,MenuId;
    public AddToCartModel(String menuId) {
        Quantity="1";
        MenuId=menuId;
    }
    public AddToCartModel() {
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getMenuId() {
        return MenuId;
    }
}
