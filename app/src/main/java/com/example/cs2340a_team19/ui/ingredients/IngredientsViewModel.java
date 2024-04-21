package com.example.cs2340a_team19.ui.ingredients;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.AggregateDataHandler;
import com.example.cs2340a_team19.models.DataUpdateListener;
import com.example.cs2340a_team19.models.Database;
import com.example.cs2340a_team19.models.Ingredient;

import java.util.List;

public class IngredientsViewModel extends ViewModel {

    private Database dbHandler;
    private AggregateDataHandler<Ingredient> pantryHandler;
    private final IngredientsFragment fragment;
    private final DataUpdateListener<List<Ingredient>> updateUI;
    public IngredientsViewModel(IngredientsFragment fragment) {

        this.fragment = fragment;
        this.dbHandler = Database.getInstance();
        this.pantryHandler = dbHandler.getPantryHandler();
        this.updateUI = (pantry) -> fragment.updatePantry(pantry, this);
        this.pantryHandler.addDataUpdateListener(updateUI);

    }

    public void updateIngredientQuantity(Ingredient ing, int quantity) {
        ing.setQuantity(quantity);
        this.pantryHandler.update(ing);
    }

    public void removeIngredient(Ingredient ing) {
        this.pantryHandler.remove(ing);
    }

    public void onViewDestroyed() {
        this.pantryHandler.removeDataUpdateListener(updateUI);
    }
}