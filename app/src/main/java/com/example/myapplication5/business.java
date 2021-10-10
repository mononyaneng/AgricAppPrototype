package com.example.myapplication5;

public class business {
    private String tradeNames;
    private String TIN;
    private String[] phone = new String[]{};
    private String[] emails = new String[]{};
    private String physicalAdrr;
    private String logoImgUrl;

    public business(){}
    public business(String tradeNames,String TIN, String phone,
                    String email, String physicalAdrr, String logoImgUrl){
        this.tradeNames = tradeNames;
        this.TIN = TIN;
        this.phone[0] = phone;
        this.emails[0] = email;
        this.physicalAdrr = physicalAdrr;
        this.logoImgUrl = logoImgUrl;
    }

    public void setTradeNames(String tradeNames) {
        this.tradeNames = tradeNames;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public void setPhones(String[] phone) {
        this.phone = phone;
    }

    public void setPhone(String phone) {
        this.phone[0] = phone;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    public void setEmail(String email){ this.emails[0] = email;}

    public void setPhysicalAdrr(String physicalAdrr) {
        this.physicalAdrr = physicalAdrr;
    }

    public void setLogoImgUrl(String logoImgUrl) {
        this.logoImgUrl = logoImgUrl;
    }

    public String getTradeNames() {
        return tradeNames;
    }

    public String getTIN() {
        return TIN;
    }

    public String[] getPhones() {
        return phone;
    }
    public String getPhone(){ return this.phone[0];}

    public String[] getEmails() {
        return emails;
    }
    public String getEmail(){ return  this.emails[0];}

    public String getPhysicalAdrr() {
        return physicalAdrr;
    }

    public String getLogoImgUrl() {
        return logoImgUrl;
    }
}
