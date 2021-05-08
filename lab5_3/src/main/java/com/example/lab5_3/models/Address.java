package com.example.lab5_3.models;

import com.google.gson.annotations.SerializedName;

public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    @SerializedName("geo")
    private Geo Geo;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public com.example.lab5_3.models.Geo getGeo() {
        return Geo;
    }

    public void setGeo(com.example.lab5_3.models.Geo geo) {
        Geo = geo;
    }
}
