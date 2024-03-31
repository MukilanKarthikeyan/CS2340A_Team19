package com.example.cs2340a_team19.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PantryHandler {
    private DatabaseReference pantries;
    private boolean successfullyInitialized;
    public PantryHandler (DatabaseReference db) {
        try {
            this.pantries = db.child("pantries");
        } catch (NullPointerException ne) {
            Log.d("FBRTDB_ERROR", "Getting reference for pantries reached Null Pointer!");
            return;
        }

        successfullyInitialized = true;
    }

    // Get Data for meal
    public void listenToPantry(String userId, ValueEventListener updater) {
        if (successfullyInitialized) {
            this.pantries.child(userId).addValueEventListener(updater);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to attach event listener, but View Model was not successfully instantiated");
        }
    }

    public void updateIngredientQuantity(String userId, String ingredientId, int quantity) {
        if (successfullyInitialized) {
            try {
                pantries.child(userId).child(ingredientId).child("quantity").setValue(quantity);
            } catch (NullPointerException npe) {
                Log.d("FBRTDB_ERROR", "Tried to add ingredient but encountered null pointer with (userId, ingredient Id, quantity): " + userId + ", " + ingredientId + ", " + quantity);
            }


        } else {
            Log.d("FBRTDB_ERROR", "Tried to update ingredient but the View Model was not sucsessfully instantiated!");
        }
    }

    /**
     *
     * @return String representing the mealID of the new Meal
     */
    public String createIngredient(String userId) {
        Ingredient ingredient = new Ingredient();
        return this.createIngredient(userId, ingredient);
    }

    /**
     *
     * @return String representing the mealID of the new Meal
     */
    public String createIngredient(String userId, String name, int calories, int quantity) {
        Ingredient ingredient = new Ingredient(name, calories, quantity);
        return this.createIngredient(userId, ingredient);
    }

    /**
     *
     * @return String representing the mealID of the new Meal
     */
    public String createIngredient(String userId, String name, String expirationDate, int calories, int quantity) {
        Ingredient ingredient = new Ingredient(name, calories, quantity, expirationDate);
        return this.createIngredient(userId, ingredient);
    }

    /**
     *
     * @return String representing the mealID of the new Meal
     */
    private String createIngredient(String userId, Ingredient ingredient) {
        if (successfullyInitialized) {
            DatabaseReference childLoc = pantries.child(userId).push();
            // Find and set ID with Regex
//            Pattern pattern = Pattern.compile("/(\\d*)$");
//            Matcher matcher = pattern.matcher(childLoc.getKey());
//            if (matcher.find()) {
//                meal.setMealID(matcher.group());
//            } else {
//                Log.d("FBRTDB_ERROR", "Tried to identify mealID but failed: " + childLoc.getKey());
//            }
            ingredient.ingredientID = childLoc.getKey();
            childLoc.setValue(ingredient);
            return childLoc.getKey();
        } else {
            Log.d("FBRTDB_ERROR", "Tried to create ingredient but the View Model was not sucsessfully instantiated!");
            return null;
        }
    }

    public void removeIngredient(String userId, String ingredientId) {
        this.pantries.child(userId).child(ingredientId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.d("FBRTDB_ERROR", "Tried to remove ingredient " + ingredientId +
                            " from user " + userId + " but was unsuccsessful");
                }
            }
        });
    }

    public boolean isSuccessfullyInitialized() {
        return successfullyInitialized;
    }
}
