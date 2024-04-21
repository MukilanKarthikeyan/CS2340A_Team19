package com.example.cs2340a_team19.ui.ingredients;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.FragmentIngredientsBinding;
import com.example.cs2340a_team19.models.AggregateDataHandler;
import com.example.cs2340a_team19.models.Database;
import com.example.cs2340a_team19.models.Ingredient;

import java.util.List;

public class IngredientsFragment extends Fragment {

    private FragmentIngredientsBinding binding;
    private Button formButton;

    private RecyclerView recyclerView;
    private IngredientsViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentIngredientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        formButton = root.findViewById(R.id.formButton);
        formButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(
                        R.id.action_navigation_ingredients_to_ingredientFeaturesFragment);
            }
        });
        return root;
    }
    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {

        // Initialize RecyclerView
        this.recyclerView = view.findViewById(R.id.ingredientsRecycler);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.vm = new IngredientsViewModel(this);
    }

    public void updatePantry(List<Ingredient> pantry) {
        if (this.recyclerView != null) {
            this.recyclerView.setAdapter(new IngredientsAdapter(pantry, vm));
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        vm.onViewDestroyed();
        binding = null;
    }
}
