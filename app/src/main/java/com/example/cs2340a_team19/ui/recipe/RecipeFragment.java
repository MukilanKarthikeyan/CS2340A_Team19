package com.example.cs2340a_team19.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.example.cs2340a_team19.databinding.FragmentNotificationsBinding;
import com.example.cs2340a_team19.databinding.FragmentRecipeBinding;

import java.util.ArrayList;
import java.util.Collections;

public class RecipeFragment extends Fragment {
    //this will need to be intitialized as either a sortReverseAlphabetical or sortAlphabetical concrete strategy instance
    private static recipeSorter recipeSortingStrategy;
    private FragmentRecipeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecipeViewModel recipeViewModel =
                new ViewModelProvider(this).get(RecipeViewModel.class);

        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRecipe;
        recipeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<String> sortRecipes() {
        return recipeSorter.sortRecipes(ArrayList<Recipe> recipes);
    }
}