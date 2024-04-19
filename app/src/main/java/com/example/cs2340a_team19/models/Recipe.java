package com.example.cs2340a_team19.models;

import com.google.firebase.database.Exclude;

import java.util.List;
import java.util.ArrayList;

public class Recipe extends Aggregatable {
    @Exclude
    private boolean pantryReady;
    private String name;
    private String userId;
    private int calories;
    private String description;
    private List<Ingredient> ingredients;

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
                this.setCalories(this.getCalories() + ingredient.getCalories());
            }
        }
    }

    public Recipe(String name, String userId, String description,
                  int calories, List<Ingredient> ingredients) {
        this.setName(name);
        this.setUserId(userId);
        this.setCalories(calories);
        this.setIngredients(ingredients);
    }

    public String getName() {
        return name;
    }
    public String getLCName() {
        return getName().toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isPantryReady() {
        return pantryReady;
    }

    public void setPantryReady(boolean pantryReady) {
        this.pantryReady = pantryReady;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
