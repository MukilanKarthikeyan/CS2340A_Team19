package com.example.cs2340a_team19.ui.meals;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.Meal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MealsViewModel extends ViewModel {
    private DatabaseReference meals;
    private boolean successfullyInstantiated;
    public MealsViewModel () {
        try{
            this.meals = FirebaseDatabase.getInstance().getReference().child("meals");
        } catch (NullPointerException ne) {
            Log.d("FBRTDB_ERROR", "Getting reference for meals reached Null Pointer!");
            return;
        }

        successfullyInstantiated = true;
    }

    // Get Data for meal
    public void attachMealEventListener(ValueEventListener updater, String mealID) {
        if (successfullyInstantiated) {
            this.meals.child(mealID).addValueEventListener(updater);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to attach event listener, but View Model was not successfully instantiated");
        }
    }
    public void updateMeal(String mealID, String name, int calories) {
        Meal meal = new Meal(mealID, name, calories);
        if (successfullyInstantiated) {
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
        if (successfullyInstantiated) {
            DatabaseReference childLoc = meals.push();
            // Find and set ID with Regex
            Pattern pattern = Pattern.compile("/(\\d*)$");
            Matcher matcher = pattern.matcher(childLoc.getKey());
            if (matcher.find()) {
                meal.setMealID(matcher.group());
            } else {
                Log.d("FBRTDB_ERROR", "Tried to identify mealID but failed");
            }

            childLoc.setValue(meal);
            return meal.getMealID();
        } else {
            Log.d("FBRTDB_ERROR", "Tried to update/create meal but the View Model was not sucsessfully instantiated!");
            return null;
        }
    }

}