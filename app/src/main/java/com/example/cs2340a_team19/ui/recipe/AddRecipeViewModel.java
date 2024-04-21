package com.example.cs2340a_team19.ui.recipe;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.AggregateDataHandler;
import com.example.cs2340a_team19.models.Database;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeViewModel extends ViewModel {
    private Database dbHandler;
    private AggregateDataHandler<Recipe> cookbookHandler;
    private AddRecipeFragment fragment;

    public AddRecipeViewModel(AddRecipeFragment fragment) {
        this.fragment = fragment;
        this.dbHandler = Database.getInstance();
        this.cookbookHandler = dbHandler.getCookbookHandler();
    }

    public void addRecipe(String name, String[] ingredientNames, int[] quantities) {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        if (ingredientNames == null || quantities == null
                || ingredientNames.length != quantities.length) {
            Log.d("VM_ERROR", "The ingredientName list and the ingredient quantity list "
                    + "were not the right size");
            return;
        }
        for (int i = 0; i < ingredientNames.length; i++) {
            ingredients.add(new Ingredient(ingredientNames[i], 0, quantities[i]));
        }
        this.cookbookHandler.append(new Recipe(name, dbHandler.getUserID(),
                "", 0, ingredients));
    }
}
