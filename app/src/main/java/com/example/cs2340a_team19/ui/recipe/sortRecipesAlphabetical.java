package com.example.cs2340a_team19.ui.recipe;

import java.util.ArrayList;
import java.util.Collections;

public class sortRecipesAlphabetical implements recipeSorter{
    public ArrayList<String> sortRecipes(ArrayList<Recipe> recipes) {
        ArrayList<String> recipeNames = new ArrayList<>();

        for (Recipe curr : recipes) {
            String currName = curr.getName();
            recipeNames.add(currName);
        }

        Collections.sort(recipeNames);

        return recipeNames;
    }
}
