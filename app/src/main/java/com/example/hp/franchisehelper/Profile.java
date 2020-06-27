package com.example.hp.franchisehelper;

public class Profile {
    String name,leftover,received,sold,price;

    public Profile() {
    }

    public Profile(String name, String leftover, String recieved, String sold,String price) {
        this.name = name;
        this.leftover = leftover;
        this.received = recieved;
        this.sold = sold;
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

    public String getName() {
        return name;
    }



    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getLeftover() {
        return leftover;
    }

    public void setLeftover(String leftover) {
        this.leftover = leftover;
    }
}
