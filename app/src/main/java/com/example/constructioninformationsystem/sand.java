package com.example.constructioninformationsystem;

public class sand {
    String supplier,price,brand;
     sand(String s, String p, String b){
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
