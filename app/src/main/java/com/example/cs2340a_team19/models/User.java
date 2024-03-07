package com.example.cs2340a_team19.models;

import java.util.ArrayList;

public class User {
    private String userID;
    private String email;
    private int height;
    private int weight;
    private boolean gender;

    private ArrayList<String> mealIDs;

    public User() {
        userID = "";
        email = "";
        height = -1;
        weight = -1;
        gender = false;
        mealIDs = new ArrayList<String>;
    }

    public User(String uid, String email, int height, int weight, boolean gender) {
        this.userID = uid;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        mealIDs = new ArrayList<String>;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String uid) {
        this.userID = uid;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean getGender() {
        return this.gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getMealID(int index) {
        if (index < 0 || index >= mealIDs.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mealIDs.size());
        }
        return this.mealIDs.get(index);
    }

    public void addMealID(String mealID) {
        this.mealIDs.add(mealID);
    }

    public boolean removeMealIDVal(int mealID) {
        return mealIDs.remove(String.valueOf(mealID)); // Autoboxing for Integer
    }

    public String removeMealIDIndex(int index) {
        if (index < 0 || index >= mealIDs.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mealIDs.size());
        }
        return mealIDs.remove(index);
    }
}
