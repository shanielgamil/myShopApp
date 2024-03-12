package com.example.myshoppingapp.models;

import android.view.View;

public class User {
    String email;
    String password;
    String phoneNumber;
    String age;
    String name;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }



    public Integer finalAmount=0;

    public User(String email, String password, String phoneNumber, String age, String name) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.name = name;

    }
    public Integer getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Integer finalAmount) {
        this.finalAmount = finalAmount;
    }
   public User(){
      // this.email = "bob@gmail.com";
      // this.password = "1234";
     //  this.phoneNumber = "0523456789";
      // this.age = "20";
      // this.name = "bob";
   }
}







