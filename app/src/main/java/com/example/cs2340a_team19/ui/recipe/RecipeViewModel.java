package com.example.cs2340a_team19.ui.recipe;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.CookbookHandler;
import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.PantryHandler;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.models.Recipe;
import com.example.cs2340a_team19.models.Recommendation;
import com.example.cs2340a_team19.ui.meals.MealsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewModel extends ViewModel {

    private DatabaseHandler dbHandler;
    private CookbookHandler cookbookHandler;
    private PantryHandler pantryHandler;

    public RecipeViewModel() {
        this.dbHandler = DatabaseHandler.getInstance();
        this.cookbookHandler = dbHandler.getCookbookHandler();
        this.pantryHandler = dbHandler.getPantryHandler();

        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            // Add event listeners here!
            addCookbookListener();
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile because dbHandler Initialization Failed!");
        }
    }

    public void addRecipe(String name, String[] ingredientNames, int[] quantities) {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        if (ingredientNames == null || quantities == null || ingredientNames.length != quantities.length) {
            Log.d("VM_ERROR", "The ingredientName list and the ingredient quantity list were not the right size");
            return;
        }
        for (int i = 0; i < ingredientNames.length; i++) {
            ingredients.add(new Ingredient(ingredientNames[i], 0, quantities[i]));
        }
        this.cookbookHandler.createRecipe(new Recipe(name, dbHandler.getUserID(), "", 0, ingredients));
    }

    public void addCookbookListener() {
        this.cookbookHandler.listenToCookbook(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    recipes.add(postSnapshot.getValue(Recipe.class));
                }
                // TODO: Call UI updater and pass in recipes!
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FBRTDB_ERROR", "Tried to add cookbook listner but cancelled");
            }
        });
    }
}