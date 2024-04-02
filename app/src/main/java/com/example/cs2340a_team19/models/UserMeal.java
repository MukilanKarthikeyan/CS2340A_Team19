package com.example.cs2340a_team19.models;

public class UserMeal {
    private String mealId;
    private String date;

    public UserMeal() {
        this("", "");
    }

    public UserMeal(String mealId, String date) {
        this.mealId = mealId;
        this.date = date;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
