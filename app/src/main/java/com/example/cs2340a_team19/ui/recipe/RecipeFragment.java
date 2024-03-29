package com.example.cs2340a_team19.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    private FragmentRecipeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecipeViewModel recipeViewModel =
                new ViewModelProvider(this).get(RecipeViewModel.class);

        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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