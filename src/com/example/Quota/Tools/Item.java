package com.example.Quota.Tools;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    private String name;

    private double cost;

    public Item(String name, double cost){
        this.name = name;
        this.cost = cost;
    }

    private Item(Parcel in){
        name = in.readString();
        cost = in.readDouble();
    }

    public String name(){
        return name;
    }

    public double cost(){
        return -1 * cost;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(cost);
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

}
