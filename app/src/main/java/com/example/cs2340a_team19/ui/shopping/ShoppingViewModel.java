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
import java.util.function.BiConsumer;

public class ShoppingViewModel extends ViewModel {
    private DatabaseHandler dbHandler;
    private CookbookHandler cookbookHandler;
    private ShoppingListHandler shoppingListHandler;
    private List<Ingredient> currentList;
    private BiConsumer<List<Ingredient>, ShoppingViewModel> updateUI;
    public ShoppingViewModel(BiConsumer<List<Ingredient>, ShoppingViewModel> updateUI) {
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
        ShoppingViewModel vm = this;
        this.shoppingListHandler.listenToShoppingList(dbHandler.getUserID(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Ingredient> shoppingList = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    shoppingList.add(postSnapshot.getValue(Ingredient.class));
                }
                currentList = shoppingList;
                if (updateUI != null) {
                    updateUI.accept(currentList, vm);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FBRTDB_ERROR", "Tried to add shopping listener but cancelled");
            }
        });
    }

    public boolean incrementIngredientQuantity(String ingID) {
        int targetIndex = -1;
        for (int i = 0; i < currentList.size(); i++) {
            if (ingID.equals(currentList.get(i).getIngredientID())) {
                targetIndex = i;
                break;
            }
        }
        if (targetIndex == -1) {
            return false;
        }
        incrementIngredientQuantity(targetIndex);
        return true;
    }

    public void incrementIngredientQuantity(int index) {
        this.shoppingListHandler.updateIngredientQuantity(dbHandler.getUserID(),
                currentList.get(index).getIngredientID(), currentList.get(index).getQuantity() + 1);
    }

    public boolean decrementIngredientQuantity(String ingID) {
        int targetIndex = -1;
        for (int i = 0; i < currentList.size(); i++) {
            if (ingID.equals(currentList.get(i).getIngredientID())) {
                targetIndex = i;
                break;
            }
        }
        if (targetIndex == -1) {
            return false;
        }
        decrementIngredientQuantity(targetIndex);
        return true;
    }

    public void decrementIngredientQuantity(int index) {
        if (this.currentList.get(index).getQuantity() <= 1) {
            this.shoppingListHandler.removeIngredient(dbHandler.getUserID(), currentList.get(index).getIngredientID());
        } else {
            this.shoppingListHandler.updateIngredientQuantity(dbHandler.getUserID(),
                    currentList.get(index).getIngredientID(), currentList.get(index).getQuantity() - 1);
        }
    }

    public void addIngredient(String name, int quantity) {
        // Check for duplicate ingredient
        for (Ingredient curr : currentList) {
            if (curr.getName().trim().equalsIgnoreCase(name.trim())) {
                this.shoppingListHandler.updateIngredientQuantity(dbHandler.getUserID(), curr.getIngredientID(), curr.getQuantity() + quantity);
                return;
            }
        }
        shoppingListHandler.createIngredient(
                dbHandler.getUserID(), name, 0,quantity);
    }

}