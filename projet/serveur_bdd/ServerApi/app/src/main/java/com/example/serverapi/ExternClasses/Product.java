package com.example.serverapi.ExternClasses;

public class Product {
    private String name;
    private String productImageUrl;
    private double price;
    private String description;
    private String productTags;


    public Product(String name, String productImageUrl, double price) {
        this.name = name;
        this.productImageUrl = productImageUrl;
        this.price = price;
    }

    public Product(String name, String productImageUrl, double price, String description) {
        this.name = name;
        this.productImageUrl = productImageUrl;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductTags() {
        return productTags;
    }

    public void setProductTags(String productTags) {
        this.productTags = productTags;
    }
}
