package com.example.cs2340a_team19.models;

public class Ingredient {
    public String ingredientID;
    public String name;
    public int calories;
    public int quantity;
    public String expirationDate;

    public Ingredient() {
        this("", 0, 1, null);
    }
    public Ingredient(String name, int calories, int quantity) {
        this(name, calories, quantity, null);
    }
    public Ingredient(String name, int calories, int quantity, String expirationDate) {
        this.name = name;
        this.calories = calories;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }
}
