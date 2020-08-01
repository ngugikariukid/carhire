package com.example.carloan;

public class OrdersModel {
    public OrdersModel(){
        //Empty constructor
    }
    String bookerEmail;
    String orderkey;
    String orderDate;
    String CarId;
    String pickDate;
    String returnDate;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    Boolean status;
    int totalPrice;

    public OrdersModel(String orderKey, String orderDate, String bookerEmail, String CarId, String pickDate, String returnDate, int totalPrice, Boolean status){
        this.bookerEmail = bookerEmail;
        this.orderkey = orderKey;
        this.orderDate = orderDate;
        this.CarId = CarId;
        this.pickDate = pickDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public String getOrderkey() {
        return orderkey;
    }

    public void setOrderkey(String orderkey) {
        this.orderkey = orderkey;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCarId() {
        return CarId;
    }

    public void setCarId(String carId) {
        CarId = carId;
    }

    public String getPickDate() {
        return pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
