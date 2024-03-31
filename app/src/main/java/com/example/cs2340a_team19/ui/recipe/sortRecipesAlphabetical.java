package com.example.cs2340a_team19.ui.recipe;
import com.example.cs2340a_team19.models.Recipe;

import java.util.ArrayList;
import java.util.Comparator;

public class sortRecipesAlphabetical implements recipeSorter{
    public ArrayList<Recipe> sortRecipes(ArrayList<Recipe> recipes) {
        ArrayList<Recipe> sortedRecipe = new ArrayList<>(recipes);
        sortedRecipe.sort(Comparator.comparing(Recipe::getName));
        return sortedRecipe;
    }
}
