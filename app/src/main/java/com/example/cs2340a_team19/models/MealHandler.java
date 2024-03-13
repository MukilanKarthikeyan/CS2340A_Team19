package com.example.cs2340a_team19.models;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MealHandler {
    private DatabaseReference meals;
    private boolean successfullyInitialized;
    public MealHandler (DatabaseReference db) {
        try{
            this.meals = db.child("meals");
        } catch (NullPointerException ne) {
            Log.d("FBRTDB_ERROR", "Getting reference for meals reached Null Pointer!");
            return;
        }

        successfullyInitialized = true;
    }

    // Get Data for meal
    public void listenToMeal(String mealID, ValueEventListener updater) {
        if (successfullyInitialized) {
            this.meals.child(mealID).addValueEventListener(updater);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to attach event listener, but View Model was not successfully instantiated");
        }
    }
    public void updateMeal(String mealID, String name, int calories) {
        Meal meal = new Meal(mealID, name, calories);
        if (successfullyInitialized) {
            meals.child(mealID).setValue(meal);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to update meal but the View Model was not sucsessfully instantiated!");
        }
    }

    /**
     *
     * @return String representing the mealID of the new Meal
     */
    public String createMeal() {
        Meal meal = new Meal();
        return this.createMeal(meal);
    }

    /**
     *
     * @return String representing the mealID of the new Meal
     */
    public String createMeal(String name, int calories) {
        Meal meal = new Meal(name, calories);
        return this.createMeal(meal);
    }

    /**
     *
     * @return String representing the mealID of the new Meal
     */
    private String createMeal(Meal meal) {
        if (successfullyInitialized) {
            DatabaseReference childLoc = meals.push();
            // Find and set ID with Regex
//            Pattern pattern = Pattern.compile("/(\\d*)$");
//            Matcher matcher = pattern.matcher(childLoc.getKey());
//            if (matcher.find()) {
//                meal.setMealID(matcher.group());
//            } else {
//                Log.d("FBRTDB_ERROR", "Tried to identify mealID but failed: " + childLoc.getKey());
//            }

            meal.setMealID(childLoc.getKey());

            childLoc.setValue(meal);
            return meal.getMealID();
        } else {
            Log.d("FBRTDB_ERROR", "Tried to update/create meal but the View Model was not sucsessfully instantiated!");
            return null;
        }
    }

    public boolean isSuccessfullyInitialized() {
        return successfullyInitialized;
    }

}