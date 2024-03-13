package com.example.cs2340a_team19.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile {
//    private String userID;
    private int height; //centimeters
    private int weight; //kilograms
    private boolean gender; //male is true, female is false

    private List<String> mealIDs;

    public Profile() {
//        userID = "";
        height = -1;
        weight = -1;
        gender = false;
        mealIDs = new ArrayList<String>();
    }

    public Profile(int height, int weight, boolean gender) {
//        this.userID = uid;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        mealIDs = new ArrayList<String>();
    }

//    public String getUserID() {
//        return this.userID;
//    }
//
//    public void setUserID(String uid) {
//        this.userID = uid;
//    }

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

    public List<String> getMealIDs() {
        return this.mealIDs;
    }

    public void setMealIDs(List<String> mealIDs) {
        this.mealIDs = mealIDs;
    }

    @Exclude
    public String getMealID(int index) {
        if (index < 0 || index >= mealIDs.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mealIDs.size());
        }
        return this.mealIDs.get(index);
    }

    @Exclude
    public void addMealID(String mealID) {
        this.mealIDs.add(mealID);
    }

    @Exclude
    public boolean removeMealIDVal(int mealID) {
        return mealIDs.remove(String.valueOf(mealID)); // Autoboxing for Integer
    }

    @Exclude
    public String removeMealIDIndex(int index) {
        if (index < 0 || index >= mealIDs.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mealIDs.size());
        }
        return mealIDs.remove(index);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("height", this.height);
        result.put("weight", this.weight);
        result.put("gender", this.gender);

        return result;
    }
}
