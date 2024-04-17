package com.example.cs2340a_team19.ui.shopping;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.CookbookHandler;
import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.ShoppingListHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ShoppingViewModel extends ViewModel {
    private DatabaseHandler dbHandler;
    private CookbookHandler cookbookHandler;
    private ShoppingListHandler shoppingListHandler;
    private List<Ingredient> currentList;
    private Consumer<List<Ingredient>> updateUI;
    public ShoppingViewModel(Consumer<List<Ingredient>> updateUI) {
        this.updateUI = updateUI;
        this.dbHandler = DatabaseHandler.getInstance();
        this.cookbookHandler = dbHandler.getCookbookHandler();
        this.shoppingListHandler = dbHandler.getShoppingListHandler();
        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            addShoppingListener();
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile, "
                    + "because dbHandler Initialization Failed!");
        }
    }

    public void addShoppingListener() {
        this.shoppingListHandler.listenToShoppingList(dbHandler.getUserID(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Ingredient> shoppingList = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    shoppingList.add(postSnapshot.getValue(Ingredient.class));
                }
                currentList = shoppingList;
                if (updateUI != null) {
                    updateUI.accept(currentList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FBRTDB_ERROR", "Tried to add cookbook listner but cancelled");
            }
        });
    }

    public void addIngredient(String name, int quantity) {
        shoppingListHandler.createIngredient(
                dbHandler.getUserID(), name, 0,quantity);
    }

}