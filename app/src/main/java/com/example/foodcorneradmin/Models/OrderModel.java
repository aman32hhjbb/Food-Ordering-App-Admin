package com.example.foodcorneradmin.Models;

public class OrderModel {
    String UserId,DeliveryStatus;
    String TotalAmount,Quantity,OrderId;
    String TimeStamp;




    public OrderModel( String userId, String deliveryStatus, String totalAmount,String quantity,String orderId,String timeStamp) {
        UserId = userId;
        DeliveryStatus = deliveryStatus;
        TotalAmount = totalAmount;
        Quantity=quantity;
        OrderId=orderId;
        TimeStamp=timeStamp;
    }
    public OrderModel(){

    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDeliveryStatus() {
        return DeliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        DeliveryStatus = deliveryStatus;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

}
