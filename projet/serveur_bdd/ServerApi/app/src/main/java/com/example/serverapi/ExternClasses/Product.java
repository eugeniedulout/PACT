package com.example.serverapi.ExternClasses;

public class Product {
    private String name;
    private String productImageUrl;
    private double price;
    private boolean inStock;
    private String description;
    private String productTags;


    public Product(String name, String productImageUrl, double price) {
        this.name = name;
        this.productImageUrl = productImageUrl;
        this.price = price;
    }

    public Product(String name, String productImageUrl, double price, boolean inStock, String description) {
        this.name = name;
        this.productImageUrl = productImageUrl;
        this.price = price;
        this.inStock = inStock;
        this.description = description;
    }

}
