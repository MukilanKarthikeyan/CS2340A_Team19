package com.example.cs2340a_team19.ui.recipe;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.AggregateDataHandler;
import com.example.cs2340a_team19.models.Database;
import com.example.cs2340a_team19.models.Recipe;

public class AddRecipeViewModel extends ViewModel {
    private Database dbHandler;
    private AggregateDataHandler<Recipe> cookbookHandler;
    private AddRecipeFragment fragment;

    public AddRecipeViewModel(AddRecipeFragment fragment) {
        this.fragment = fragment;
        this.dbHandler = Database.getInstance();
        this.cookbookHandler = dbHandler.getCookbookHandler();
    }

    public void addRecipe(Recipe recipe) {
        recipe.setUserId(this.dbHandler.getUserID());
        this.cookbookHandler.append(recipe);
    }
}
