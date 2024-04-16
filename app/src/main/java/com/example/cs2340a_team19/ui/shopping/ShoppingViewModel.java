package com.example.cs2340a_team19.ui.shopping;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.CookbookHandler;
import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.ShoppingListHandler;

import java.util.List;
import java.util.function.Consumer;

public class ShoppingViewModel extends ViewModel {
    private DatabaseHandler dbHandler;
    private CookbookHandler cookbookHandler;
    private ShoppingListHandler shoppingListHandler;
    public ShoppingViewModel(Consumer<List<Ingredient>> updateUI) {
        this.dbHandler = DatabaseHandler.getInstance();
        this.cookbookHandler = dbHandler.getCookbookHandler();
        this.shoppingListHandler = dbHandler.getShoppingListHandler();
        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            Log.d("PLACEHOLDER", "Gryph add an event listener");
            // Add event listeners here!
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile, "
                    + "because dbHandler Initialization Failed!");
        }
    }

    public void addIngredient(String name, int quantity) {
        shoppingListHandler.createIngredient(
                dbHandler.getUserID(), name, 0,quantity);
    }

}