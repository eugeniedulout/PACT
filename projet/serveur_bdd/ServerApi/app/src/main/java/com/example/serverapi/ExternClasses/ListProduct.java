package com.example.serverapi.ExternClasses;

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
        try {
            this.listName = json_list.getString("name");
            JSONArray json_products = json_list.getJSONArray("products");
            for (int i = 0; i < json_list.length(); i++) {
                JSONObject json_product = json_products.getJSONObject(i);
                this.listOfProducts.add(new Product(json_product));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}