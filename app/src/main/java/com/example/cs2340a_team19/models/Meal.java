package com.example.cs2340a_team19.models;

public class Meal extends Aggregatable {
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
        this.name = name;
        this.calories = calories;
        this.date = date;
    }

    public Meal(Meal other) {
        if (other == null) {
            return;
        }
        this.name = other.name;
        this.calories = other.calories;
        this.date = other.date;
        super.setId(other.getId());
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
