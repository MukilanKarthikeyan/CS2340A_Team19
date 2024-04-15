package com.example.cs2340a_team19.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ShoppingListHandler {
    private DatabaseReference shoppingLists;
    private boolean successfullyInitialized;
    public ShoppingListHandler(DatabaseReference db) {
        try {
            this.shoppingLists = db.child("shoppingLists");
        } catch (NullPointerException ne) {
            Log.d("FBRTDB_ERROR", "Getting reference for shoppingLists reached Null Pointer!");
            return;
        }

        successfullyInitialized = true;
    }

    // Get Data for meal
    public void listenToShoppingList(String userId, ValueEventListener updater) {
        if (successfullyInitialized) {
            this.shoppingLists.child(userId).addValueEventListener(updater);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to attach event listener, "
                    + "but View Model was not successfully instantiated");
        }
    }

    public void updateIngredientQuantity(String userId, String ingredientId, int quantity) {
        if (successfullyInitialized) {
            try {
                shoppingLists.child(userId).child(ingredientId).child("quantity").setValue(quantity);
            } catch (NullPointerException npe) {
                Log.d("FBRTDB_ERROR", "Tried to add ingredient, "
                        + "but encountered null pointer with (userId, ingredient Id, quantity): "
                        + userId + ", " + ingredientId + ", " + quantity);
            }


        } else {
            Log.d("FBRTDB_ERROR", "Tried to update ingredient, "
                    + "but the View Model was not sucsessfully instantiated!");
        }
    }

    /**
     * @param userId userId
     * @return String representing the mealID of the new Meal
     */
    public String createIngredient(String userId) {
        Ingredient ingredient = new Ingredient();
        return this.createIngredient(userId, ingredient);
    }

    /**
     * @param userId userId
     * @param name name
     * @param calories calories
     * @param quantity quantity
     * @return String representing the mealID of the new Meal
     */
    public String createIngredient(String userId, String name, int calories, int quantity) {
        Ingredient ingredient = new Ingredient(name, calories, quantity);
        return this.createIngredient(userId, ingredient);
    }

    /**
     * @param userId userId
     * @param name name
     * @param expirationDate expirationDate
     * @param calories calories
     * @param quantity quantity
     * @return String representing the mealID of the new Meal
     */
    public String createIngredient(String userId, String name, String expirationDate,
                                   int calories, int quantity) {
        Ingredient ingredient = new Ingredient(name, calories, quantity, expirationDate);
        return this.createIngredient(userId, ingredient);
    }

    /**
     * @param userId userId
     * @param ingredient ingredient
     * @return String representing the mealID of the new Meal
     */
    private String createIngredient(String userId, Ingredient ingredient) {
        if (successfullyInitialized) {
            DatabaseReference childLoc = shoppingLists.child(userId).push();
            // Find and set ID with Regex
            // Pattern pattern = Pattern.compile("/(\\d*)$");
            // Matcher matcher = pattern.matcher(childLoc.getKey());
            // if (matcher.find()) {
            //     meal.setMealID(matcher.group());
            // } else {
            //     Log.d("FBRTDB_ERROR", "Tried to identify mealID but failed: "
            //             + childLoc.getKey());
            // }
            ingredient.setIngredientID(childLoc.getKey());
            // Log.d("GRYPHON_TEST", ingredient.ingredientID);
            childLoc.setValue(ingredient);
            return childLoc.getKey();
        } else {
            Log.d("FBRTDB_ERROR", "Tried to create ingredient, "
                    + "but the View Model was not sucsessfully instantiated!");
            return null;
        }
    }

    public void removeIngredient(String userId, String ingredientId) {
        this.shoppingLists.child(userId).child(ingredientId).removeValue().addOnCompleteListener(
                new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Log.d("FBRTDB_ERROR", "Tried to remove ingredient " + ingredientId
                                + " from user " + userId + " but was unsuccsessful");
                    }
                }
            });
    }

    public boolean isSuccessfullyInitialized() {
        return successfullyInitialized;
    }
}
