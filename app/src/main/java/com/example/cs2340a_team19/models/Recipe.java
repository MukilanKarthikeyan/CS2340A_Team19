package com.example.cs2340a_team19.models;

import com.google.firebase.database.Exclude;

import java.util.List;
import java.util.ArrayList;

public class Recipe {
    @Exclude
    public boolean pantryReady;
    public String name;
    public String userId;
    public int calories;
    public String description;
    public List<Ingredient> ingredients;

    public Recipe() {
        this("", "", "");
    }

    public Recipe(String name, String userId, String description) {
        this(name, userId, description, new ArrayList<Ingredient>(0));
    }

    public Recipe(String name, String userId, String description, List<Ingredient> ingredients) {
        this(name, userId, description, 0, ingredients);

        if (ingredients != null) {
            for (Ingredient ingredient : ingredients) {
                this.calories += ingredient.calories;
            }
        }
    }

    public Recipe(String name, String userId, String description, int calories, List<Ingredient> ingredients) {
        this.name = name;
        this.userId = userId;
        this.calories = calories;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }
    public String getLCName() { return name.toLowerCase(); }
}
