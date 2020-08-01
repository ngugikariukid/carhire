package com.example.carloan;

public class RegisterModel {
    public RegisterModel(){

    }

    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDl() {
        return dl;
    }

    public void setDl(String dl) {
        this.dl = dl;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    String firstname;
    String lastname;
    String phone;
    String dl;
    String pass;

    public RegisterModel(String email, String firstname, String lastname, String phone, String dl, String pass){
        this.email=email;
        this.firstname=firstname;
        this.lastname=lastname;
        this.phone=phone;
        this.dl=dl;
        this.pass=pass;
    }


}
