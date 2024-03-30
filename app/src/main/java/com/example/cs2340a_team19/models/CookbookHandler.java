package com.example.cs2340a_team19.models;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CookbookHandler {
    private DatabaseReference cookbook;
    private boolean successfullyInitialized;
    public CookbookHandler (DatabaseReference db) {
        try{
            this.cookbook = db.child("cookbook");
        } catch (NullPointerException ne) {
            Log.d("FBRTDB_ERROR", "Getting reference for cookbook reached Null Pointer!");
            return;
        }

        successfullyInitialized = true;
    }

    public void listenToCookbook(ValueEventListener updater) {
        if (successfullyInitialized) {
            this.cookbook.addValueEventListener(updater);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to attach event listener, but View Model was not successfully instantiated");
        }
    }
    public void listenToRecipe(String recipeId, ValueEventListener updater) {
        if (successfullyInitialized) {
            this.cookbook.child(recipeId).addValueEventListener(updater);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to attach event listener, but View Model was not successfully instantiated");
        }
    }

    // Placeholder
    public void updateRecipe(String recipeId, Recipe recipe) {
        if (successfullyInitialized) {
            cookbook.child(recipeId).setValue(recipe);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to update recipe but the View Model was not sucsessfully instantiated!");
        }
    }

    /**
     *
     * @return String representing the mealID of the new Meal
     */
    public String createRecipe() {
        Recipe recipe = new Recipe();
        return this.createRecipe(recipe);
    }

    /**
     *
     * @return String representing the mealID of the new Meal
     */
    public String createRecipe(String userId, String name, String description) {
        Recipe recipe = new Recipe(name, userId, description);
        return this.createRecipe(recipe);
    }

    /**
     *
     * @return String representing the mealID of the new Meal
     */
    private String createRecipe(Recipe recipe) {
        if (successfullyInitialized) {
            DatabaseReference childLoc = cookbook.push();
            // Find and set ID with Regex
//            Pattern pattern = Pattern.compile("/(\\d*)$");
//            Matcher matcher = pattern.matcher(childLoc.getKey());
//            if (matcher.find()) {
//                meal.setMealID(matcher.group());
//            } else {
//                Log.d("FBRTDB_ERROR", "Tried to identify mealID but failed: " + childLoc.getKey());
//            }

            childLoc.setValue(recipe);
            return childLoc.getKey();
        } else {
            Log.d("FBRTDB_ERROR", "Tried to create recipe but the View Model was not sucsessfully instantiated!");
            return null;
        }
    }

    public boolean isSuccessfullyInitialized() {
        return successfullyInitialized;
    }
}
