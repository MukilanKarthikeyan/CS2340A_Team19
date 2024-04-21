package com.example.cs2340a_team19.models;

public class Meal extends Aggregatable {
    private String mealID;
    private String name;
    private int calories;
    private String date;

    public Meal() {
        this("", "", -1, "");
    }
    public Meal(String name, int calories, String date) {
        this("", name, calories, date);
    }

    public Meal(String mealID, String name, int calories, String date) {
        this.mealID = mealID;
        this.name = name;
        this.calories = calories;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
