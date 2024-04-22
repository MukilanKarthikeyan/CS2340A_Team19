package com.example.cs2340a_team19.ui.recipe;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.AggregateDataHandler;
import com.example.cs2340a_team19.models.DataUpdateListener;
import com.example.cs2340a_team19.models.Database;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewModel extends ViewModel {
    private Database dbHandler;
    private AggregateDataHandler<Recipe> cookbookHandler;
    private AggregateDataHandler<Ingredient> pantryHandler;
    private AggregateDataHandler<Ingredient> shoppingListHandler;
    private RecipeFragment fragment;
    private RecipeSorter sortingStrategy;
    private DataUpdateListener<List<Recipe>> cookbookListener;

    public RecipeViewModel(RecipeFragment fragment, RecipeSorter sortingStrategy) {
        this.fragment = fragment;
        this.sortingStrategy = sortingStrategy;
        this.dbHandler = Database.getInstance();
        this.cookbookHandler = dbHandler.getCookbookHandler();
        this.pantryHandler = dbHandler.getPantryHandler();
        this.shoppingListHandler = dbHandler.getShoppingListHandler();

        this.cookbookListener = (cookbook) -> {
            updateRecipeList(cookbook);
            sortCookbook(cookbook);
        };
        this.cookbookHandler.addDataUpdateListener(this.cookbookListener);
    }

    public void updateRecipeList(List<Recipe> cookbook) {
        for (Recipe curr : cookbook) {
            curr.setPantryReady(checkRecipe(curr));
        }
    }

    public boolean checkRecipe(Recipe recipe) {
        for (Ingredient curr : recipe.getIngredients()) {
            boolean matchedIngredient = false;
            for (Ingredient pantryIngredient : pantryHandler.getData()) {
                if (curr.equals(pantryIngredient)) {
                    if (curr.getQuantity() <= pantryIngredient.getQuantity()) {
                        matchedIngredient = true;
                    }
                    break;
                }
            }
            if (!matchedIngredient) {
                return false;
            }
        }
        return true;
    }

    public void addIngredientToShoppingList(Ingredient ingredient) {
        // Check for duplicate ingredient
        List<Ingredient> staticList = new ArrayList<>(shoppingListHandler.getData());
        List<Ingredient> pantryList = new ArrayList<>(pantryHandler.getData());
        Ingredient updatedIngredient = new Ingredient(ingredient);
        for (Ingredient pantryIng : pantryList) {
            if (updatedIngredient.equals(pantryIng)) {
                updatedIngredient.setQuantity(ingredient.getQuantity() - pantryIng.getQuantity());
            }
        }
        if (updatedIngredient.getQuantity() <= 0) {
            return;
        }
        for (Ingredient curr : staticList) {
            if (updatedIngredient.equals(curr)) {
                Ingredient staticCurr = new Ingredient(curr);
                staticCurr.setQuantity(curr.getQuantity() + updatedIngredient.getQuantity());
                this.shoppingListHandler.update(staticCurr);
                return;
            }
        }
        shoppingListHandler.append(updatedIngredient);
    }
    public void addRecipeToShoppingList(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            addIngredientToShoppingList(ingredient);
        }
    }

    public void cookRecipe(Recipe recipe) {
        List<Ingredient> staticPantry = new ArrayList<>(this.pantryHandler.getData());
        for (Ingredient ingredient : recipe.getIngredients()) {
            for (Ingredient curr : staticPantry) {
                if (ingredient.equals(curr)) {
                    Ingredient staticCurr = new Ingredient(curr);
                    staticCurr.setQuantity(curr.getQuantity() - ingredient.getQuantity());
                    pantryHandler.update(staticCurr);
                }
            }
        }
    }

    public void sortCookbook(List<Recipe> cookbook) {
        sortingStrategy.sortRecipes(cookbook);
        fragment.updateData(cookbook, this.pantryHandler.getData(), this);
    }

    public void setSortingStrategy(RecipeSorter strategy) {
        this.sortingStrategy = strategy;
        this.sortCookbook(this.cookbookHandler.getData());
    }

    public void onViewDestroyed() {
        this.cookbookHandler.removeDataUpdateListener(this.cookbookListener);
    }
}
