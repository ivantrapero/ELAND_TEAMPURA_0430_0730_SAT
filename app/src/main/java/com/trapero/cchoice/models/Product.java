package com.trapero.cchoice.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int id;
    private String name;
    private double price;
    private int discount;
    private float rating;
    private int reviewCount;
    private String imageResId;
    private String description;

    // Constructor
    public Product(int id, String name, double price, int discount, float rating, int reviewCount, String imageResId, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.imageResId = imageResId;
        this.description = description != null ? description : ""; // Ensure description is never null
    }

    // Constructor for Parcel
    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        discount = in.readInt();
        rating = in.readFloat();
        reviewCount = in.readInt();
        imageResId = in.readString();
        description = in.readString(); // Read description from Parcel
    }

    // Parcelable CREATOR
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public float getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public String getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }

    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);  // Ensure to write 'id' as well.
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(discount);
        dest.writeFloat(rating);
        dest.writeInt(reviewCount);
        dest.writeString(imageResId);
        dest.writeString(description); // Write description to Parcel
    }
}
