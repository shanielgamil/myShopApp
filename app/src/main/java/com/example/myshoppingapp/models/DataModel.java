package com.example.myshoppingapp.models;

public class DataModel {

    private String name;
    private String price;
    private int image; // Integer
    private int id_;
    private int amount;

    public DataModel(String name,String price,int image,int id_,int amount) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.id_ = id_;
        this.amount=amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }






    public int getAmount() {
        return amount;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getId_() {
        return id_;
    }

    public int getImage() {
        return image;
    }
}








