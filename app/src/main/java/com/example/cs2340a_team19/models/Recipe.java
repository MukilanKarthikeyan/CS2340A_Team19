package com.example.cs2340a_team19.models;

import java.util.List;
import java.util.ArrayList;

public class Recipe {
    public String name;
    public String userId;
    public int calories;
    public List<Ingredient> ingredients;

    public Recipe() {
        this("", "");
    }

    public Recipe(String name, String userId) {
        this(name, userId, new ArrayList<Ingredient>(0));
    }

    public Recipe(String name, String userId, List<Ingredient> ingredients) {
        this.name = name;
        this.userId = userId;
        this.calories = 0;
        this.ingredients = ingredients;

        for (Ingredient ingredient : ingredients) {
            this.calories += ingredient.calories;
        }
    }
}
