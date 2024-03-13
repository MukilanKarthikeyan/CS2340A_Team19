package com.example.cs2340a_team19.models;

public class UserMeal {
    public String mealId;
    public String date;

    public UserMeal() {
        this("", "");
    }

    public UserMeal(String mealId, String date) {
        this.mealId = mealId;
        this.date = date;
    }
}
