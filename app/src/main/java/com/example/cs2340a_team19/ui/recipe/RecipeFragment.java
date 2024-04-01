package com.example.cs2340a_team19.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.cs2340a_team19.databinding.FragmentNotificationsBinding;
import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.FragmentRecipeBinding;

import com.example.cs2340a_team19.models.Recipe;
import com.example.cs2340a_team19.ui.ingredients.IngredientsAdapter;
import com.example.cs2340a_team19.ui.meals.MealsViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Collections;

import java.util.List;
import java.util.TimeZone;

public class RecipeFragment extends Fragment {
    //this will need to be intitialized as either a sortReverseAlphabetical or sortAlphabetical concrete strategy instance
    private static recipeSorter recipeSortingStrategy;
    private FragmentRecipeBinding binding;
    private Button newRecipeButton;
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> recipeList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        newRecipeButton = root.findViewById(R.id.newRecipeButton);
        newRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_recipe_to_add_recipe_fragment);
            }

        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
        //createPieChart(view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_recipe_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecipeViewModel viewModel = new RecipeViewModel((recipeList) -> recyclerView.setAdapter(new RecipeAdapter(recipeList)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
//
//    public List<String> sortRecipes() {
//        return recipeSorter.sortRecipes(List<Recipe> recipes);
//    }
}