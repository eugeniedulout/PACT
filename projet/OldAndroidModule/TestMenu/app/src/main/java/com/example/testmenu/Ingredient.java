package com.example.testmenu;

public class Ingredient {
    private String name;
    private String imageUrl;


    public Ingredient(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }


}
