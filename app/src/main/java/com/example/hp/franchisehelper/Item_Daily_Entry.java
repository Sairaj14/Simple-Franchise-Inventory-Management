package com.example.hp.franchisehelper;

public class Item_Daily_Entry {

    String received, leftover, sold, name,price;

    Item_Daily_Entry(String name, String received, String leftover, String sold,String price) {
        this.leftover = leftover;
        this.received = received;
        this.sold = sold;
        this.name = name;
        this.price=price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getLeftover() {
        return leftover;
    }

    public void setLeftover(String leftover) {
        this.leftover = leftover;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
