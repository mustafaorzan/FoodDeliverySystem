package com.example.fooddeliverysystem;

public class CourierModel {
    private String blood, email, lastName, name, password, vehicle;
    public CourierModel(){}

    public CourierModel(String blood, String email, String lastName, String name, String password, String vehicle) {
        this.blood = blood;
        this.email = email;
        this.lastName = lastName;
        this.name = name;
        this.password = password;
        this.vehicle = vehicle;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
