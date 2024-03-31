package com.example.cs2340a_team19.models;

public class Ingredient {
    public String ingredientID;
    public String name;
    public int calories;
    public int quantity;

    public Ingredient() {
        this.name = "";
        this.calories = 0;
        this.quantity = 0;
    }

    public Ingredient(String name, int calories, int quantity) {
        this.name = name;
        this.calories = calories;
        this.quantity = quantity;
    }
}
