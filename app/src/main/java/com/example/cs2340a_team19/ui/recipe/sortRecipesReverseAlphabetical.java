package com.example.cs2340a_team19.ui.recipe;
import com.example.cs2340a_team19.models.Recipe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class sortRecipesReverseAlphabetical implements recipeSorter{
    public ArrayList<String> sortRecipes(ArrayList<Recipe> recipes) {
        ArrayList<String> recipeNames = new ArrayList<>();

        for (Recipe curr : recipes) {
            String currName = curr.getName();
            recipeNames.add(currName);
        }

        Collections.sort(recipeNames);

        Collections.reverse(recipeNames);

        return recipeNames;
    }
}
