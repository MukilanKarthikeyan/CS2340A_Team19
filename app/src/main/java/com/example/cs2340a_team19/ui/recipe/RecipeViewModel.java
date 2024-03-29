package com.example.cs2340a_team19.ui.recipe;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.CookbookHandler;
import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.PantryHandler;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.models.Recommendation;
import com.example.cs2340a_team19.ui.meals.MealsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile because dbHandler Initialization Failed!");
        }
    }
}