package com.example.foodcorneradmin.Models;

public class MenuModel {
    String MenuId,ItemName,ItemPrice,ItemImage,ItemDescription;

    public MenuModel() {
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getItemImage() {
        return ItemImage;
    }

    public void setItemImage(String itemImage) {
        ItemImage = itemImage;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public MenuModel(String menuId, String itemName, String itemPrice, String itemImage, String itemDescription) {
        MenuId = menuId;
        ItemName = itemName;
        ItemPrice = itemPrice;
        ItemImage = itemImage;
        ItemDescription = itemDescription;
    }
}
