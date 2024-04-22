package com.example.cs2340a_team19.ui.shopping;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.AggregateDataHandler;
import com.example.cs2340a_team19.models.Database;
import com.example.cs2340a_team19.models.Ingredient;
import java.util.List;

public class ShoppingViewModel extends ViewModel {
    private Database dbHandler;
    private AggregateDataHandler<Ingredient> shoppingListHandler;
    private AggregateDataHandler<Ingredient> pantryHandler;
    private ShoppingFragment fragment;
    public ShoppingViewModel(ShoppingFragment fragment) {
        this.fragment = fragment;
        this.dbHandler = Database.getInstance();
        this.shoppingListHandler = dbHandler.getShoppingListHandler();
        this.pantryHandler = dbHandler.getPantryHandler();
        if (dbHandler.isSuccessfullyInitialized()) {
            this.shoppingListHandler.addDataUpdateListener((list) -> fragment.updateUI(list, this));
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile, "
                    + "because dbHandler Initialization Failed!");
        }
    }

    public void incrementIngredientQuantity(int index) {
        Ingredient target = this.shoppingListHandler.getData().get(index);
        target.setQuantity(target.getQuantity() + 1);
        this.shoppingListHandler.update(target);
    }

    public void decrementIngredientQuantity(int index) {
        Ingredient target = this.shoppingListHandler.getData().get(index);
        if (target.getQuantity() <= 1) {
            this.shoppingListHandler.remove(target);
        } else {
            target.setQuantity(target.getQuantity() - 1);
            this.shoppingListHandler.update(target);
        }
    }

    public void addIngredient(String name, int quantity) {
        Ingredient newIng = new Ingredient(name, 0, quantity);
        // Check for duplicate ingredient
        if (shoppingListHandler.getData() != null) {
            for (Ingredient curr : shoppingListHandler.getData()) {
                if (curr.getName().trim().equalsIgnoreCase(name.trim())) {
                    newIng.setQuantity(quantity + curr.getQuantity());
                    this.shoppingListHandler.update(newIng);
                    return;
                }
            }
        }
        shoppingListHandler.append(newIng);
    }

    public void buy(List<Ingredient> toBuy) {
        for (Ingredient curr : toBuy) {
            this.shoppingListHandler.remove(curr);
            List<Ingredient> currPantry = this.pantryHandler.getData();

            boolean wasInList = false;
            for (Ingredient pantryIngredient : currPantry) {
                if (curr.equals(pantryIngredient)) {
                    pantryIngredient.setQuantity(pantryIngredient.getQuantity()
                            + curr.getQuantity());
                    this.pantryHandler.update(pantryIngredient);
                    wasInList = true;
                    break;
                }
            }
            if (!wasInList) {
                this.pantryHandler.append(curr);
            }
        }
    }
}