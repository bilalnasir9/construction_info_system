package com.example.constructioninformationsystem;

public class concrete {
    String supplier,price,brand;
     concrete(String s, String p, String b){
        this.supplier=s;
        this.price=p;
        this.brand=b;

    }


    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    public String getSupplier() {
        return supplier;
    }
}
