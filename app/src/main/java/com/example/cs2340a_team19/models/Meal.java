package com.example.cs2340a_team19.models;

public class Meal {
    private String mealID;
    private String name;
    private int calories;

    public Meal() {
        this("", "", -1);
    }
    public Meal(String name, int calories) {
        this("", name, calories);
    }

    public Meal(String mealID, String name, int calories) {
        this.mealID = mealID;
        this.name = name;
        this.calories = calories;
    }

    public String getMealID() {
        return this.mealID;
    }

    public void setMealID(String mealID) {
        this.mealID = mealID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return this.calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
