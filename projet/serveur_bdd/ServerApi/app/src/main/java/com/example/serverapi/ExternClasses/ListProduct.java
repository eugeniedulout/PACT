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

    public JSONObject toJSON() {
        JSONObject array = new JSONObject();
        try {
            JSONArray shared = new JSONArray();
            shared.put(2);
            array.put("shared", shared);

            JSONObject list = new JSONObject();
            list.put("name", this.listName);

            JSONArray products = new JSONArray();
            for(Product p : this.listOfProducts) {
                products.put(p.toJSON());
            }

            list.put("products",products);
            array.put("list", list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    @NonNull
    @Override
    public String toString() {
        String str ="LIST NAME: "+this.listName+"\n";
        for(Product p : this.listOfProducts) {
            str+="\t"+p.toString()+"\n";
        }
        return str;
    }
}