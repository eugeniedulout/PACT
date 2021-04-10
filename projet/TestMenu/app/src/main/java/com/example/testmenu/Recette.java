package com.example.testmenu;

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

    public String getRecetteName() {
        return recetteName;
    }

    public ArrayList<Ingredient> getListOfIngredients() {
        return listOfIngredients;
    }

    public ArrayList<String> getCookingInstructions() {
        return cookingInstructions;
    }

}
