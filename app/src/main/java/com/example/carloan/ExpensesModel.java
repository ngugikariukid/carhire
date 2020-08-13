package com.example.carloan;

public class ExpensesModel {
    public ExpensesModel(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    String email;
    int amount;
    public ExpensesModel(String email, int amount){
        this.email = email;
        this.amount = amount;
    }
}
