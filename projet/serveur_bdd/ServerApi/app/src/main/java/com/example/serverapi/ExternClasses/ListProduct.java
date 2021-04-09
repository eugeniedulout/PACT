package com.example.serverapi.ExternClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListProduct {

    private String listName;
    private ArrayList<Product> listOfProducts;

    public ListProduct(String listName, ArrayList<Product> listOfProducts) {
        this.listName = listName;
        this.listOfProducts = listOfProducts;
    }

    public ListProduct(JSONObject json_list) {
        this.listOfProducts = new ArrayList<Product>();
        try {
            this.listName = json_list.getString("name");
            JSONArray json_products = json_list.getJSONArray("products");
            for (int i = 0; i < json_products.length(); i++) {
                JSONObject json_product = json_products.getJSONObject(i);
                this.listOfProducts.add(new Product(json_product));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public String toString() {
        String str ="";
        for(Product p : this.listOfProducts) {
            str+="\t"+p.toString()+"\n";
        }
        return str;
    }
}