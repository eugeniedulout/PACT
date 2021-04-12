package com.example.testmenu;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;


public class Ingredient {
    private String name;
    private String ingredientImageUrl;


    public String getName() {
        return name;
    }

    public String getIngredientImageUrl() {
        return ingredientImageUrl;
    }

    public Ingredient(String name, String ingredientImageUrl) {
        this.name = name;
        this.ingredientImageUrl = ingredientImageUrl;
    }

    public Ingredient(JSONObject json_ingredient) {
        try {
            this.name = json_ingredient.getString("name");
            this.ingredientImageUrl = json_ingredient.getString("img_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public JSONObject toJSON() throws JSONException {
        JSONObject json_ingredient = new JSONObject();
        json_ingredient.put("name", this.name);
        json_ingredient.put("img_url", this.ingredientImageUrl);

        return json_ingredient;
    }

    @NonNull
    @Override
    public String toString() {
        return "name: " + this.name + " // img_url: " + this.ingredientImageUrl;
    }
}


