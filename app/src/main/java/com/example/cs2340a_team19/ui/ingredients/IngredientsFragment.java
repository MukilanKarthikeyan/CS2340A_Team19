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
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.cs2340a_team19.R;
    import com.example.cs2340a_team19.databinding.FragmentIngredientsBinding;
    import com.example.cs2340a_team19.models.Ingredient;
    import com.example.cs2340a_team19.models.PantryHandler;
    import com.example.cs2340a_team19.ui.recipe.RecipeViewModel;

    import java.util.ArrayList;
    import java.util.List;

    public class IngredientsFragment extends Fragment {

        private FragmentIngredientsBinding binding;
        private Button formButton;

        private RecyclerView recyclerView;
        private IngredientsAdapter adapter;
        private List<Ingredient> ingredientArr;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            IngredientsViewModel ingredientsViewModel =
                    new ViewModelProvider(this).get(IngredientsViewModel.class);

            binding = FragmentIngredientsBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            //final TextView textView = binding.textIngredients;
            formButton = root.findViewById(R.id.formButton);
            formButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_ingredients_to_ingredientFeaturesFragment);
                }

            });
            //ingredientsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            return root;
        }
        @Override
        public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
            //createPieChart(view);
            IngredientsViewModel viewModel = new IngredientsViewModel();

            // Initialize RecyclerView
            recyclerView = view.findViewById(R.id.ingredientsRecycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // Initialize ingredient list (you should populate this with actual data)
            ingredientArr = new ArrayList<>();

            PantryHandler.listenToPantry()

            // Initialize adapter
            adapter = new IngredientsAdapter(ingredientList);

            // Set adapter to RecyclerView
            recyclerView.setAdapter(adapter);
        }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }
