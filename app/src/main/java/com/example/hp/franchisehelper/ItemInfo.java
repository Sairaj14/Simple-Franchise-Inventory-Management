package com.example.hp.franchisehelper;

public class ItemInfo {
    String price,name;
    ItemInfo(String name,String price)
    {
        this.price=price;
        this.name=name;


    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}