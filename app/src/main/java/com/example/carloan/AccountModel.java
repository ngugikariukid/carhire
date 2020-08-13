package com.example.carloan;

public class AccountModel {

public AccountModel(){
    //empty C
}

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    String userEmail;
    String balance;

public AccountModel(String userEmail, String balance){
    this.userEmail = userEmail;
    this.balance = balance;
}


}
