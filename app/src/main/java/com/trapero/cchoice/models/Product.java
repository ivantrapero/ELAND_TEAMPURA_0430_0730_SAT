package com.trapero.cchoice.models;

public class Product {

    private String name;
    private double price;
    private int discount;
    private float rating;
    private int reviewCount;
    private int imageResId;

    public Product(String name, double price, int discount, float rating, int reviewCount, int imageResId) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.imageResId = imageResId;
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

    public int getImageResId() {
        return imageResId;
    }
}
