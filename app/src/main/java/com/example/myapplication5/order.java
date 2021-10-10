package com.example.myapplication5;

import java.util.Date;

public class order {
    private String buyerId;
    private String sellerId;
    private Date dueDate;
    private String orderStatusId;
    private String notes;

    public order(){}
    public order(String buyerId,String sellerId, Date dueDate, String orderStatusId, String notes){
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.dueDate = dueDate;
        this.orderStatusId = orderStatusId;
        this.notes = notes;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public void setOrderStatusId(String orderStatusId) {
        this.orderStatusId = orderStatusId;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBuyerId() {
        return buyerId;
    }
    public String getSellerId() {
        return sellerId;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public String getOrderStatusId() {
        return orderStatusId;
    }
    public String getNotes() {
        return notes;
    }
}
