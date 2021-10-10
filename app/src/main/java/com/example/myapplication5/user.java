package com.example.myapplication5;

public class user {
    private String names;
    private String surname;
    private String phone;
    private String email;
    private String physicalAddr;
    private String businessId = "none";
    private String nationalId;
    private String profilePicUrl;

    public user(){}
    public user( String name, String surname, String phone,String email, String physicalAddr,
    String businessId, String nationalId, String profilePicUrl){
        this.names = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.physicalAddr = physicalAddr;
        this.businessId = businessId;
        this.nationalId = nationalId;
        this.profilePicUrl = profilePicUrl;
    }

    public void setName(String name){
        this.names=name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public  void setEmail(String email){ this.email = email;}
    public void setPhysicalAddr(String physicalAddr) {
        this.physicalAddr = physicalAddr;
    }
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getName(){
        return this.names;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getPhone(){ return this.phone;}
    public String getEmail(){ return  this.email;}
    public String getPhysicalAddr() {
        return physicalAddr;
    }
    public String getBusinessId() {
        return businessId;
    }
    public String getNationalId() {
        return nationalId;
    }
    public String getProfilePicUrl() {
        return profilePicUrl;
    }
}
