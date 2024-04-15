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
    private MutableLiveData<String> textLiveData;
    public IngredientsViewModel() {
        this.dbHandler = DatabaseHandler.getInstance();
        this.cookbookHandler = dbHandler.getCookbookHandler();
        this.pantryHandler = dbHandler.getPantryHandler();
        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            Log.d("PLACEHOLDER", "Gryph add an event listener");
            // Add event listeners here!
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile, "
                    + "because dbHandler Initialization Failed!");
        }
        this.textLiveData = new MutableLiveData<>();
        this.textLiveData.setValue("Default text");
    }
    public LiveData<String> getText() {
        return textLiveData;
    }

    public void addIngredient(String name, int quantity, int calories, String expiration) {
        if (expiration != null && !expiration.equals("")) {
            pantryHandler.createIngredient(
                    dbHandler.getUserID(), name, expiration, calories, quantity);
        } else {
            pantryHandler.createIngredient(dbHandler.getUserID(), name, calories, quantity);
        }
    }
    // Method to update the text
    public void updateText(String newText) {
        textLiveData.setValue(newText);
    }

}