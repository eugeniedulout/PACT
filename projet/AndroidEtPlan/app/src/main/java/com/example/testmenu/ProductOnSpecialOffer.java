package com.example.testmenu;

import android.content.Context;

import com.example.testmenu.fragments.ProductOnSpecialOfferInfoFragment;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductOnSpecialOffer extends Product {

    private double newPrice;
    private String expirationDate;


    public double getNewPrice() {
        return newPrice;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public ProductOnSpecialOffer(String name, String productImage, double price, double newPrice, String expirationDate) {
        super(name, productImage, price);
        this.newPrice = newPrice;
        this.expirationDate = expirationDate;
    }

    public ProductOnSpecialOffer(String name, String productImage, double price, String description, double newPrice, String expirationDate) {
        super(name, productImage, price, description);
        this.newPrice = newPrice;
        this.expirationDate = expirationDate;

    }

    public ProductOnSpecialOffer(JSONObject json_product) {
        super(json_product);

        try {
            this.newPrice = json_product.getDouble("new_price");
            this.expirationDate = json_product.getString("expiration_date");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public String toString() {
        String str = super.toString();
        str += "\n\tnew_price: " + this.newPrice;
        str += "\n\texpiration_date: " + this.expirationDate;
        return str;
    }
    public void displayInfo(Context context) {
        FragmentController.swapFragmentInMainContainer(new ProductOnSpecialOfferInfoFragment(this), context);
    }
}

