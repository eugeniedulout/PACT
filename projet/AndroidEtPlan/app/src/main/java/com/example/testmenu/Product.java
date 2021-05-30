package com.example.testmenu;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.testmenu.fragments.ProductInfoFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Product implements Serializable{
    protected String name;
    protected String productImageUrl;
    protected double price;
    protected String description;
    protected String productTags;

    protected double x;
    protected double y;
    protected int z;


    public Product(String name, String productImageUrl, double price) {
        this.name = name;
        this.productImageUrl = productImageUrl;
        this.price = price;
    }

    public Product(String name, String productImageUrl, double price, double x, double y, int z) {
        this(name, productImageUrl, price);
        this.x = x;
        this.y = y;
        this.z = z;
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
            this.x = json_product.getDouble("x");
            this.y = json_product.getDouble("y");
            this.z = json_product.getInt("z");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", this.getName());
            json.put("img_url", this.productImageUrl);
            json.put("price", this.getPrice());
            json.put("description", this.getDescription());
            json.put("x", this.getX());
            json.put("y", this.getY());
            json.put("z", this.getZ());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @NonNull
    @Override
    public String toString() {
        String str = "";
        str += "\tname: " + this.getName();
        str += "\n\timg_url: " + this.getProductImageUrl();
        str += "\n\tprice: " + this.getPrice();
        str += "\n\tdescription: " + this.getDescription();
        str += "\n\tcoords : x=" + this.x + " y=" + this.y + "z=" + this.z;
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void displayInfo(Context context, int containerId) {
        FragmentController.swapFragment(new ProductInfoFragment(this), containerId, context);
    }

    public boolean isOnPromotion() {
        return  false;
    }
    public void multiplyByQuantity(int quantite) {
        this.price = price * quantite;
        this.name = name + " x" + String.valueOf(quantite);
    }
}





