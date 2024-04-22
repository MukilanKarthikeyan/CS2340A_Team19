package com.example.cs2340a_team19.ui.recipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecipeFragment extends Fragment {

    // Please DO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Please DO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;

    public AddRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecipeFragment.
     */
    // Please DO: Rename and change types and number of parameters
    public static AddRecipeFragment newInstance(String param1, String param2) {
        AddRecipeFragment fragment = new AddRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
        //createPieChart(view);
        AddRecipeViewModel viewModel = new AddRecipeViewModel(this);
        Button addRecipe = view.findViewById(R.id.addRecipeButton);
        EditText recipeName = view.findViewById(R.id.recipe_name_field);
        EditText recipeDescription = view.findViewById(R.id.add_recipe_description);

        EditText addIngredientName = view.findViewById(R.id.add_recipe_add_item_name);
        EditText addIngredientQuantity = view.findViewById(R.id.add_recipe_add_item_quant);
        EditText addIngredientCalories = view.findViewById(R.id.add_recipe_add_item_calorie);
        Button addIngredientButton = view.findViewById(R.id.add_recipe_add_item_button);

        List<Ingredient> ingredientList = new ArrayList<>();

        this.recyclerView = view.findViewById(R.id.recycler_add_recipe_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new AddRecipeIngredientsAdapter(ingredientList));

        addIngredientButton.setOnClickListener((v) -> {
            Ingredient ingredient = Ingredient.parseIngredient(
                    addIngredientName.getText().toString(),
                    addIngredientQuantity.getText().toString(),
                    addIngredientCalories.getText().toString(), "");
            if (ingredient == null) {
                Toast.makeText(getContext(), "Invalid Ingredient.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Ingredient Added!",
                        Toast.LENGTH_SHORT).show();
                ingredientList.add(ingredient);
                recyclerView.setAdapter(new AddRecipeIngredientsAdapter(ingredientList));
                addIngredientName.setText("");
                addIngredientQuantity.setText("");
                addIngredientCalories.setText("");
            }
        });



        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recName = recipeName.getText().toString();
                String recDescription = recipeDescription.getText().toString();


                //if any input is empty, display this problem to user
                if (recName.isEmpty() || recDescription.isEmpty()) {
                    Toast.makeText(getContext(), "You have an empty input. Cannot add to recipe.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ingredientList.size() == 0) {
                    Toast.makeText(getContext(), "You must have ingredients in the recipe!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Recipe recipe = Recipe.parseRecipe(recName, recDescription, "", ingredientList);
                if (recipe == null) {
                    Toast.makeText(getContext(), "Invalid Recipe!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                viewModel.addRecipe(recipe);
                Toast.makeText(getContext(), "Recipe Added!", Toast.LENGTH_SHORT).show();
                recipeName.setText("");
                recipeDescription.setText("");
                ingredientList.clear();
                recyclerView.setAdapter(new AddRecipeIngredientsAdapter(ingredientList));
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }
}