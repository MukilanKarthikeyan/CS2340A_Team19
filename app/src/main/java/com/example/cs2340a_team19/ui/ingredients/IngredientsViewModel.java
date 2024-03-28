package com.example.cs2340a_team19.ui.ingredients;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.CookbookHandler;
import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.PantryHandler;

public class IngredientsViewModel extends ViewModel {

    private DatabaseHandler dbHandler;
    private CookbookHandler cookbookHandler;
    private PantryHandler pantryHandler;

    public IngredientsViewModel() {
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