package com.example.cs2340a_team19.ui.recipe;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


//import com.example.cs2340a_team19.databinding.FragmentNotificationsBinding;
import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.FragmentRecipeBinding;
import com.example.cs2340a_team19.ui.meals.MealsViewModel;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class RecipeFragment extends Fragment {

    private EditText recipeName;
    private EditText ingredientList;
    private EditText quantitiesList;
    private Button addRecipe;



    private FragmentRecipeBinding binding;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);
        RecipeViewModel recipeViewModel =
                new ViewModelProvider(this).get(RecipeViewModel.class);


//        binding = FragmentRecipeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
        addRecipe = root.findViewById(R.id.addRecipe);
        if (addRecipe == null) {
            Log.d("TESTING", "add recipe not bound");
        }
        recipeName = root.findViewById(R.id.RecipeName);
        ingredientList = root.findViewById(R.id.IngredientList);
        quantitiesList = root.findViewById(R.id.QuantitiesList);


        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rec_name = recipeName.getText().toString();
                String name_list = ingredientList.getText().toString();
                String quantities_list = quantitiesList.getText().toString();


                if (rec_name.isEmpty() || name_list.isEmpty() || quantities_list.isEmpty()) {
                    Toast.makeText(getContext(), "You have an empty input. Cannot add to recipe.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                //split string based on comma, put into array
                String[] quants = quantities_list.split(",");
                for (String indivQuant : quants) {         //loop array
                    //check if something is negative or zero
                    //if so, do toast stuff
                    if (Integer.parseInt(indivQuant) <= 0) {
                        Toast.makeText(getContext(), "You have a negative quantity. " +
                                "Cannot add this to recipe.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Toast.makeText(getContext(), "Recipe added", Toast.LENGTH_SHORT).show();
                recipeName.setText("");
                ingredientList.setText("");
                quantitiesList.setText("");
            }
        });


        return root;
    }


    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
        //createPieChart(view);
        RecipeViewModel viewModel = new RecipeViewModel();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
