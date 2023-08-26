package com.example.foodcorneradmin.Models;

public class NotificationModel {
    String orderId,status,userId,notificationID,TimeStamp;
    boolean Seen;

    public NotificationModel(String orderId, String status, String userId, String notificationID,String timeStamp,boolean seen) {
        this.orderId = orderId;
        this.status = status;
        this.userId = userId;
        this.notificationID = notificationID;
        TimeStamp=timeStamp;
        Seen=seen;
    }

    public NotificationModel() {
    }

    public boolean isSeen() {
        return Seen;
    }

    public void setSeen(boolean seen) {
        Seen = seen;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }
}
