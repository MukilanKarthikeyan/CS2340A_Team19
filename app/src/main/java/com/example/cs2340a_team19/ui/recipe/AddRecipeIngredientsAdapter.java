
package com.example.cs2340a_team19.ui.recipe;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.models.Ingredient;

import java.util.List;

public class AddRecipeIngredientsAdapter extends
        RecyclerView.Adapter<AddRecipeIngredientsAdapter.IngredientViewHolder> {
    private List<Ingredient> ingredientsList;

    public AddRecipeIngredientsAdapter(List<Ingredient> itemList) {
        this.ingredientsList = itemList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_recipe_ingredients_list_item, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final IngredientViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        final Ingredient item = ingredientsList.get(position);
        holder.nameText.setText(item.getName());
        holder.quantityText.setText(String.valueOf(item.getQuantity()));
        holder.calorieText.setText(String.valueOf(item.getCalories()));
    }
    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView quantityText;
        private TextView calorieText;

        public IngredientViewHolder(View view) {
            super(view);
            nameText = view.findViewById(R.id.add_recipe_item_label);
            quantityText = view.findViewById(R.id.add_recipe_item_quantity);
            calorieText = view.findViewById(R.id.add_recipe_item_calories);
        }

        public TextView getNameText() {
            return nameText;
        }

        public void setNameText(TextView nameText) {
            this.nameText = nameText;
        }

        public TextView getQuantityText() {
            return quantityText;
        }

        public void setQuantityText(TextView quantityText) {
            this.quantityText = quantityText;
        }

        public TextView getCalorieText() {
            return calorieText;
        }

        public void setCalorieText(TextView calorieText) {
            this.calorieText = calorieText;
        }
    }
}