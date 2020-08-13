package com.example.carloan;

public class DeletedModel {
    public DeletedModel(){}

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    String orderid;
    public DeletedModel(String orderid){
        this.orderid = orderid;
    }
}
