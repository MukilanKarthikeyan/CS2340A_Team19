package com.example.cs2340a_team19.ui.recipe;
import com.example.cs2340a_team19.models.Recipe;

import java.util.Comparator;
import java.util.List;

public class SortRecipesAlphabetical implements RecipeSorter {
    public void sortRecipes(List<Recipe> recipes) {
        recipes.sort(Comparator.comparing(Recipe::getName));
    }
}
