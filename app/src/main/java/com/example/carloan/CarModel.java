package com.example.carloan;

public class CarModel {

        //Always start with the empty constructor
    public CarModel(){

    }

    //Declare the data types
    String model;
    String key;
    String year;
    String color;
    String transmission;
    String price;
    String picURL;
    Boolean availability;


    //Declare the getter and setters for the above data types

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    //Finally create the constructor with the data types above as the parameters

    public CarModel(String model, String key, String year, String color, String transmission, String price, String picURL, Boolean availability){
        this.model = model;
        this.key = key;
        this.year = year;
        this.color = color;
        this.transmission = transmission;
        this.price = price;
        this.picURL = picURL;
        this.availability = availability;
    }





}
