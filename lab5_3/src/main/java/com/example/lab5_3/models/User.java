package com.example.lab5_3.models;

import com.google.gson.annotations.SerializedName;

public class User {

    private Integer id;
    private String name;
    private String username;
    private String email;
    @SerializedName("address")
    private Address Address;
    private String phone;
    private String website;
    @SerializedName("company")
    private Company Company;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public com.example.lab5_3.models.Address getAddress() {
        return Address;
    }

    public void setAddress(com.example.lab5_3.models.Address address) {
        Address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public com.example.lab5_3.models.Company getCompany() {
        return Company;
    }

    public void setCompany(com.example.lab5_3.models.Company company) {
        Company = company;
    }
}
