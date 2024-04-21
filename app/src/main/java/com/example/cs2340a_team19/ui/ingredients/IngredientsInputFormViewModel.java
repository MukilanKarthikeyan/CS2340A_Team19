package com.example.cs2340a_team19.ui.ingredients;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.AggregateDataHandler;
import com.example.cs2340a_team19.models.Database;
import com.example.cs2340a_team19.models.Ingredient;

public class IngredientsInputFormViewModel extends ViewModel {
    private Database dbHandler;
    private AggregateDataHandler<Ingredient> pantryHandler;
    private final IngredientInputFormFragment fragment;
    public IngredientsInputFormViewModel(IngredientInputFormFragment fragment) {
        this.fragment = fragment;
        this.dbHandler = Database.getInstance();
        this.pantryHandler = dbHandler.getPantryHandler();
    }


    public void addIngredient(Ingredient ingredient) {
        pantryHandler.append(ingredient);
    }
}
