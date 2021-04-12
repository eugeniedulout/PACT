package com.example.testmenu;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.testmenu.fragments.DisplayIngredientsFromRecetteFragment;
import com.example.testmenu.fragments.DisplayedProductsFromAListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recette {
    private String recetteName;
    private ArrayList<Ingredient> listOfIngredients;
    private ArrayList<String> cookingInstructions;

    public Recette(String recetteName, ArrayList<Ingredient> listOfIngredients, ArrayList<String> cookingInstructions) {
        this.recetteName = recetteName;
        this.listOfIngredients = listOfIngredients;
        this.cookingInstructions = cookingInstructions;
    }

    public Recette(JSONObject json_recipe) {
        this.listOfIngredients = new ArrayList<Ingredient>();
        this.cookingInstructions = new ArrayList<String>();
        try {
            this.recetteName = json_recipe.getString("name");
            JSONArray json_ingredients = json_recipe.getJSONArray("ingredients");
            JSONArray json_instructions = json_recipe.getJSONArray("instructions");

            for (int i = 0; i < json_ingredients.length(); i++) {
                this.listOfIngredients.add(new Ingredient(json_ingredients.getJSONObject(i)));
            }
            for (int i = 0; i < json_instructions.length(); i++) {
                this.cookingInstructions.add(json_instructions.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getRecetteName() {
        return recetteName;
    }

    public void setRecetteName(String recetteName) {
        this.recetteName = recetteName;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json_recipe = new JSONObject();
        json_recipe.put("name",this.recetteName);

        JSONArray json_ingredients = new JSONArray();
        JSONArray json_instructions = new JSONArray();

        for(Ingredient ingredient : this.listOfIngredients) {
            json_ingredients.put(ingredient.toJSON());
        }
        for(String instruction : this.cookingInstructions) {
            json_instructions.put(instruction);
        }

        json_recipe.put("ingredients", json_ingredients);
        json_recipe.put("instructions", json_instructions);

        return json_recipe;
    }

    public ArrayList<Ingredient> getListOfIngredients() {
        return listOfIngredients;
    }

    public ArrayList<String> getCookingInstructions() {
        return cookingInstructions;
    }

    @NonNull
    @Override
    public String toString() {
        String desc = "";
        desc += "\tname: " + this.getRecetteName()+"\n";
        desc += "\tingrédients:\n";
        for(Ingredient i : this.listOfIngredients) {
            desc += "\t\t" + i.toString()+"\n";
        }
        desc += "\tInstructions:\n";
        for (String instruc : this.cookingInstructions) {
            desc += "\t\t"+instruc+"\n";
        }
        return desc;
    }
    public void displayIngredientsInTheRecette(Context context) {
        FragmentController.swapFragmentInMainContainer(new DisplayIngredientsFromRecetteFragment(this), context);
    }
}

