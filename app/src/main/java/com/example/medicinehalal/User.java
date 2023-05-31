package com.example.medicinehalal;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class User implements Serializable {

    @Exclude private String id;
    private String fullname, email, passwrod, phone, role;

    private User(){}

    private User(String fullname, String passwrod,String email, String phone, String role){
        this.fullname=fullname;
        this.passwrod=passwrod;
        this.email=email;
        this.phone=phone;
        this.role=role;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getFullname(){
        return fullname;
    }
    public void setFullname(String fullname){
        this.fullname = fullname;
    }

    public String getPassword(){
        return passwrod;
    }
    public void setPassword(String passwrod){
        this.passwrod = passwrod;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }
}
