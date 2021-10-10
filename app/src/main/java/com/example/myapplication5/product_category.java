package com.example.myapplication5;

public class product_category {
    private String catName;
    private String catDescription;

    public product_category(){}
    public product_category(String catName, String catDescription){
        this.catName =catName;
        this.catDescription = catDescription;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    public String getCatName() {
        return catName;
    }

    public String getCatDescription() {
        return catDescription;
    }
}

