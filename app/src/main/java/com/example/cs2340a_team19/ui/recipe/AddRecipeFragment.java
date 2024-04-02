package com.example.cs2340a_team19.ui.recipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import com.example.cs2340a_team19.R;

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
        RecipeViewModel viewModel = new RecipeViewModel(null);
        Button addRecipe = view.findViewById(R.id.addRecipeButton);
        EditText recipeName = view.findViewById(R.id.recipe_name_field);
        EditText ingredientList = view.findViewById(R.id.ingredient_list_field);
        EditText quantitiesListT = view.findViewById(R.id.quantity_list_field);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recName = recipeName.getText().toString();
                String nameList = ingredientList.getText().toString();
                String quantitiesList = quantitiesListT.getText().toString();

                //strip the lists of any whitespaces
                String cleanedNameList = nameList.replaceAll("\\s+", "");
                String cleanedQuantsList = quantitiesList.replaceAll("\\s+", "");


                //if any input is empty, display this problem to user
                if (recName.isEmpty() || nameList.isEmpty() || quantitiesList.isEmpty()) {
                    Toast.makeText(getContext(), "You have an empty input. Cannot add to recipe.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //split names list string based on comma, put into array
                String[] names = cleanedNameList.split(",");
                //split quantities list string based on comma, put into array
                String[] quants = cleanedQuantsList.split(",");

                if (names.length != quants.length) {
                    Toast.makeText(getContext(), "The number of items in your lists is not "
                            + "matching."
                            + "Please ensure number of items match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int[] quantsIntegers = new int[quants.length];
                //check quantities are positive
                for (int i = 0; i < quants.length; i++) {         //loop array
                    //check if something is negative or zero
                    //if so, do toast stuff
                    String curr = quants[i];
                    try {
                        quantsIntegers[i] = Integer.parseInt(curr);
                    } catch (NumberFormatException nfe) {
                        Toast.makeText(getContext(), "Invalid Quantity "
                                + "Cannot add this to recipe.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Integer.parseInt(curr) <= 0) {
                        Toast.makeText(getContext(), "You have a negative quantity. "
                                + "Cannot add this to recipe.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                viewModel.addRecipe(recName, names, quantsIntegers);
                Toast.makeText(getContext(), "Recipe added", Toast.LENGTH_SHORT).show();
                recipeName.setText("");
                ingredientList.setText("");
                quantitiesListT.setText("");
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