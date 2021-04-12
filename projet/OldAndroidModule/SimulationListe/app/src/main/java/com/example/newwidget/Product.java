package com.example.newwidget;

import android.widget.ImageView;

public class Product {
    private String name;
    private String productImage;
    private double price;
    private boolean inStock;
    private String description;



    public Product(String name, String productImage, double price) {
        this.name = name;
        this.productImage = productImage;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getProductImage() {
        return productImage;
    }

    public double getPrice() {
        return price;
    }
}
