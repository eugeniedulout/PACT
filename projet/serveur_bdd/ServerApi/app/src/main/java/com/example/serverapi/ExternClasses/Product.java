package com.example.serverapi.ExternClasses;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

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

    public Product(JSONObject json_product) {

        try {
            this.name = json_product.getString("name");
            this.productImageUrl = json_product.getString("img_url");
            this.price = json_product.getDouble("price");
            this.description = json_product.getString("description");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public String toString() {
        String str = "";
        str += "\tname: " + this.getName();
        str += "\n\timg_url: " + this.getProductImageUrl();
        str += "\n\tprice: " + this.getPrice();
        str += "\n\tdescription: " + this.getDescription();
        return str;
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
