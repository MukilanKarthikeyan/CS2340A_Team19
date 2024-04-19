package com.example.cs2340a_team19.ui.recipe;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.AggregateDataHandler;
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

    public RecipeViewModel(RecipeFragment fragment) {
        this.fragment = fragment;
        this.dbHandler = Database.getInstance();
        this.cookbookHandler = dbHandler.getCookbookHandler();
        this.pantryHandler = dbHandler.getPantryHandler();
        this.shoppingListHandler = dbHandler.getShoppingListHandler();

        this.cookbookHandler.addDataUpdateListener((cookbook) -> {
            updateRecipeList(cookbook);
            sortCookbook(cookbook);
        });
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
        for (Ingredient curr : staticList) {
            if (ingredient.equals(curr)) {
                Ingredient staticCurr = new Ingredient(curr);
                staticCurr.setQuantity(curr.getQuantity() + ingredient.getQuantity());
                this.shoppingListHandler.update(staticCurr);
                return;
            }
        }
        shoppingListHandler.append(ingredient);
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
        fragment.updateData(cookbook, this.pantryHandler.getData());
    }

    public void setSortingStrategy(RecipeSorter strategy) {
        this.sortingStrategy = strategy;
        this.sortCookbook(this.cookbookHandler.getData());
    }
}