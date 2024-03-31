    package com.example.cs2340a_team19.ui.ingredients;

    import android.os.Bundle;
    import android.util.Log;
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
    import com.example.cs2340a_team19.models.DatabaseHandler;
    import com.example.cs2340a_team19.models.Ingredient;
    import com.example.cs2340a_team19.models.PantryHandler;
    import com.example.cs2340a_team19.ui.recipe.RecipeViewModel;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.ValueEventListener;

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

            IngredientsViewModel viewModel = new IngredientsViewModel();
            DatabaseHandler dbHandler = DatabaseHandler.getInstance();
            PantryHandler pantryHandler = dbHandler.getPantryHandler();

            // Initialize RecyclerView
            recyclerView = view.findViewById(R.id.ingredientsRecycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // Initialize ingredient list (you should populate this with actual data)
            ingredientArr = new ArrayList<>();

            // Initialize adapter
            adapter = new IngredientsAdapter(ingredientArr);

            Log.d("ALEX", "ingredients list adapter set");
            // TODO fill arraylist with all ingredients - need User ID
            pantryHandler.listenToPantry(dbHandler.getUserID(), new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("ALEX", "ingredients list fragment before for loop");
                    ingredientArr.clear();
                    Log.d("ALEX", "ingredients list fragment clear list");
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        ingredientArr.add(postSnapshot.getValue(Ingredient.class));
                        Log.d("ALEX", "ingredients list fragment in for loop");
                    }
                    // Set adapter to RecyclerView
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }
