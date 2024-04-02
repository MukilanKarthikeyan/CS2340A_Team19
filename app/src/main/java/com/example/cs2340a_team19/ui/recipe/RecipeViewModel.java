package com.example.cs2340a_team19.ui.recipe;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.CookbookHandler;
import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.PantryHandler;
import com.example.cs2340a_team19.models.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class RecipeViewModel extends ViewModel {

    private DatabaseHandler dbHandler;
    private CookbookHandler cookbookHandler;
    private PantryHandler pantryHandler;
    private List<Ingredient> currentPantry;
    private List<Recipe> currentCookbook;
    private BiConsumer<List<Recipe>, List<Ingredient>> updateUI;

    public RecipeViewModel(BiConsumer<List<Recipe>, List<Ingredient>> updateUI) {
        this.dbHandler = DatabaseHandler.getInstance();
        this.cookbookHandler = dbHandler.getCookbookHandler();
        this.pantryHandler = dbHandler.getPantryHandler();
        this.currentPantry = new ArrayList<>();
        this.currentCookbook = new ArrayList<>();
        this.updateUI = updateUI;

        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            // Add event listeners here!
            addCookbookListener();
            addPantryListener();
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile because dbHandler Initialization Failed!");
        }
    }

    public void addRecipe(String name, String[] ingredientNames, int[] quantities) {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        if (ingredientNames == null || quantities == null || ingredientNames.length != quantities.length) {
            Log.d("VM_ERROR", "The ingredientName list and the ingredient quantity list were not the right size");
            return;
        }
        for (int i = 0; i < ingredientNames.length; i++) {
            ingredients.add(new Ingredient(ingredientNames[i], 0, quantities[i]));
        }
        this.cookbookHandler.createRecipe(new Recipe(name, dbHandler.getUserID(), "", 0, ingredients));
    }

    public void addCookbookListener() {
        this.cookbookHandler.listenToCookbook(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    recipes.add(postSnapshot.getValue(Recipe.class));
                }
                currentCookbook = recipes;
                updateRecipeList();

                if (updateUI != null) {
                    Log.d("GRYPHON_FINAL", "Hit updateRecipeList (Pantry): " + currentCookbook.size());
                    updateUI.accept(currentCookbook, currentPantry);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FBRTDB_ERROR", "Tried to add cookbook listner but cancelled");
            }
        });
    }

    public void addPantryListener() {
        this.pantryHandler.listenToPantry(dbHandler.getUserID(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Ingredient> pantry = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    pantry.add(postSnapshot.getValue(Ingredient.class));
                }
                currentPantry = pantry;
                updateRecipeList();
                Log.d("GryphDebug", "Hit Pantry!!! " + pantry.size());

                if (updateUI != null) {
                    Log.d("GRYPHON_FINAL", "Hit updateRecipeList (Pantry): " + currentCookbook.size());
                    updateUI.accept(currentCookbook, currentPantry);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FBRTDB_ERROR", "Tried to add cookbook listner but cancelled");
            }
        });
    }

    public void updateRecipeList() {
        for (Recipe curr : currentCookbook) {
            curr.setPantryReady(checkRecipe(curr));
        }
    }

    public boolean checkRecipe(Recipe recipe) {
        for (Ingredient curr : recipe.getIngredients()) {
            boolean matchedIngredient = false;
            for (Ingredient pantryIngredient : currentPantry) {
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

    public void sortCookbook(RecipeSorter sorter) {
        sorter.sortRecipes(currentCookbook);
        if (updateUI != null) {
            Log.d("GRYPHON_FINAL", "Hit updateRecipeList (Pantry): " + currentCookbook.size());
            updateUI.accept(currentCookbook, currentPantry);
        }
    }
}