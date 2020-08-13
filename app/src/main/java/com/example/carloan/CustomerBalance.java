package com.example.carloan;

public class CustomerBalance {
    public CustomerBalance(){}
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    int balance;
    public CustomerBalance(String email, int balance){
        this.email = email;
        this.balance = balance;
    }
}
