package com.example.cs2340a_team19.ui.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.FragmentIngredientsBinding;

public class IngredientsFragment extends Fragment {

    private FragmentIngredientsBinding binding;
    private Button formButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IngredientsViewModel ingredientsViewModel =
                new ViewModelProvider(this).get(IngredientsViewModel.class);

        binding = FragmentIngredientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textIngredients;
        formButton = root.findViewById(R.id.formButton);
        formButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_ingredients_to_ingredientFeaturesFragment);
            }

        });
        ingredientsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
        //createPieChart(view);
        IngredientsViewModel viewModel = new IngredientsViewModel();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
