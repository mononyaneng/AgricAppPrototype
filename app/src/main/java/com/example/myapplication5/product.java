package com.example.myapplication5;

import java.net.URL;

public class product {
    private String name;
    private String description;
    private double price;
    private double percentageOff;
    private double deliveryPrice;
    private String imageUrl;
    private String sellerId;
    private String subCatId;

    public product(){}
    public product(String description, double price, double percentageOff, double deliveryPrice, String imageUrl){
        this.description = description;
        this.price = price;
        this.percentageOff = percentageOff;
        this.deliveryPrice = deliveryPrice;
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description){ this.description = description;}
    public void setPrice(double price){ this.price = price;}
    public void setPercentageOff(double percentageOff){ this.percentageOff = percentageOff;}
    public void setDeliveryPrice(double deliveryPrice){ this.deliveryPrice = deliveryPrice;}
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public double getPercentageOff() {
        return percentageOff;
    }
    public double getDeliveryPrice() {
        return deliveryPrice;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getSellerId() {
        return sellerId;
    }
    public String getSubCatId() {
        return subCatId;
    }
}
