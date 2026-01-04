package com.example.basicviews;

public class Contact {
    public int id;
    public String name;
    public String phone;

    public Contact(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name + " - " + phone; // what will show in ListView
    }
}
