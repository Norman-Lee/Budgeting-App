package com.example.Quota.Tools;

public class Item {

    private String name;

    private double cost;

    public Item(String name, double cost){
        this.name = name;
        this.cost = cost;
    }

    public String name(){
        return name;
    }

    public double cost(){
        return -1 * cost;
    }


}
