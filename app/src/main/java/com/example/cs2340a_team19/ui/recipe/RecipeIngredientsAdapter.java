package com.example.cs2340a_team19.ui.recipe;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.PantryHandler;

import java.util.List;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.ViewHolder> {
    private List<Ingredient> itemList;
    private List<Ingredient> pantry;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView quantityAvailTextView;
        public TextView quantityNeededTextView;

        public ViewHolder(View view) {
            super(view);
            itemNameTextView = view.findViewById(R.id.ingredientName);
            quantityAvailTextView = view.findViewById(R.id.ingredient_quantity_available);

            quantityNeededTextView = view.findViewById(R.id.ingredient_quantity_needed);
        }
    }

    public RecipeIngredientsAdapter(List<Ingredient> itemList, List<Ingredient> pantry) {
        this.itemList = itemList;
        this.pantry = pantry;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card_ingredient, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Ingredient item = itemList.get(position);
        int quantity = 0;
        for (Ingredient pantryIng : pantry) {
            Log.d("GryphDebug", "Pantry Ing: " + ((pantryIng != null) ? pantryIng.name : "NULL"));
            if (pantryIng.equals(item)) {
                quantity = pantryIng.quantity;
                break;
            }
        }
        holder.itemNameTextView.setText(item.name);
        holder.quantityAvailTextView.setText(String.valueOf(quantity));
        holder.quantityNeededTextView.setText(String.valueOf(item.quantity));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
