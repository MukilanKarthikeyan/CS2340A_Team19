package com.example.cs2340a_team19.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs2340a_team19.MainActivity;
import com.example.cs2340a_team19.databinding.FragmentDashboardBinding;
import com.example.cs2340a_team19.ui.login.LoginActivity;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        final Button inputMealButton  = binding.InputMeal;
        final Button ingredientsButton = binding.Ingredients;
        final Button shoppingListButton = binding.shoppingList;
        final Button recipesButton = binding.Recipes;
        /*inputMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardFragment.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
        /*ingredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardFragment.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
        /*shoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardFragment.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
        /*testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardFragment.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}