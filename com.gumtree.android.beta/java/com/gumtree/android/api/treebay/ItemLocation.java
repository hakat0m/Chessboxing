package com.gumtree.android.api.treebay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemLocation {
    private String city;
    private String country;
    private String postalCode;

    public void setCity(String str) {
        this.city = str;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public void setPostalCode(String str) {
        this.postalCode = str;
    }

    public String getCity() {
        return this.city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getCountry() {
        return this.country;
    }
}
