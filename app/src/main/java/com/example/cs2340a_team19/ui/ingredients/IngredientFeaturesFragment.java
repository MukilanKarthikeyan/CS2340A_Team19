package com.example.cs2340a_team19.ui.ingredients;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cs2340a_team19.R;
public class IngredientFeaturesFragment extends Fragment {
    private EditText textIngredientName;
    private EditText textQuantity;
    private EditText textCalories;
    private EditText textExpirationDate;
    private Button addIngredient;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ingredient_features, container, false);
        textIngredientName = root.findViewById(R.id.textIngredientName);
        textQuantity = root.findViewById(R.id.textQuantity);
        textCalories = root.findViewById(R.id.textCalories);
        textExpirationDate = root.findViewById(R.id.textExpirationDate);
        addIngredient = root.findViewById(R.id.addIngredient);
        IngredientsViewModel ingredientsViewModel = new IngredientsViewModel();
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientNameStr = textIngredientName.getText().toString();
                String quantityStr = textQuantity.getText().toString();
                String caloriesStr = textCalories.getText().toString();
                String expirationDateStr = textExpirationDate.getText().toString();

                if (ingredientNameStr.isEmpty() || quantityStr.isEmpty() || caloriesStr.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter something", Toast.LENGTH_SHORT).show();
                    return;
                }
                int quantity = Integer.parseInt(quantityStr);
                double calories = Double.parseDouble(caloriesStr);
                if (quantity <= 0 || calories <= 0) {
                    Toast.makeText(getContext(), "Negative values are not possible", Toast.LENGTH_SHORT).show();
                    return;
                }

                ingredientsViewModel.addIngredient(ingredientNameStr, quantity, (int) calories, expirationDateStr);
                Toast.makeText(getContext(), "Ingredient added", Toast.LENGTH_SHORT).show();
                textIngredientName.setText("");
                textQuantity.setText("");
                textCalories.setText("");
                textExpirationDate.setText("");
            }
        });
        return root;
    }
}

