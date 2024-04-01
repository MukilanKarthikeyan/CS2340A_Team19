package com.example.cs2340a_team19.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.cs2340a_team19.databinding.FragmentNotificationsBinding;
import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.FragmentRecipeBinding;

import com.example.cs2340a_team19.models.Recipe;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecipeFragment extends Fragment {
    //this will need to be intitialized as either a sortReverseAlphabetical or sortAlphabetical concrete strategy instance
    private static RecipeSorter recipeSortingStrategy;
    private FragmentRecipeBinding binding;
    private Button newRecipeButton;
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> recipeList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //RecipeViewModel recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

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
        RecipeViewModel viewModel = new RecipeViewModel((recipeList) -> recyclerView.setAdapter(new RecipeAdapter(recipeList, getActivity())));

        Button alphaButton = view.findViewById(R.id.sortAlpha);
        Button reverseButton = view.findViewById(R.id.sortRevAlpha);
        alphaButton.setOnClickListener((v) -> viewModel.sortCookbook((list) -> list.sort(Comparator.comparing(Recipe::getName))));
        reverseButton.setOnClickListener((v) -> viewModel.sortCookbook((list) -> {
            list.sort(Comparator.comparing(Recipe::getName));
            Collections.reverse(list);
        }));
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