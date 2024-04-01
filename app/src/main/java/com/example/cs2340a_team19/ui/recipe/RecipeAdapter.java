package com.example.cs2340a_team19.ui.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private List<Recipe> recipeList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeNameTextView;
        public TextView ingredientListTextView;
        public TextView quantityListTextView;

        public ViewHolder(View view) {
            super(view);
            recipeNameTextView = view.findViewById(R.id.RecipeName);
            ingredientListTextView = view.findViewById(R.id.IngredientList);
            quantityListTextView = view.findViewById(R.id.QuantitiesList);
        }
    }

    public RecipeAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeNameTextView.setText(recipe.getName());
        holder.ingredientListTextView.setText(recipe.getIngredients());
        holder.quantityListTextView.setText(recipe.getQuantities());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
