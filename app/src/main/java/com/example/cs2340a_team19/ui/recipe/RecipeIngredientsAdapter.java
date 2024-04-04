package com.example.cs2340a_team19.ui.recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.PantryHandler;

import java.util.List;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.ViewHolder> {
    private List<Ingredient> itemList;
    private List<Ingredient> pantry;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView quantityAvailTextView;
        public TextView quantityNeededTextView;
        public CardView ingredientStatus;

        public ViewHolder(View view) {
            super(view);
            itemNameTextView = view.findViewById(R.id.ingredientName);
            quantityAvailTextView = view.findViewById(R.id.ingredient_quantity_available);

            quantityNeededTextView = view.findViewById(R.id.ingredient_quantity_needed);
            ingredientStatus = view.findViewById(R.id.recipe_ingredient_status);
        }
    }

    public RecipeIngredientsAdapter(List<Ingredient> itemList, List<Ingredient> pantry, Context context) {
        this.itemList = itemList;
        this.pantry = pantry;
        this.context = context;
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
        int quantityAvail = 0;
        for (Ingredient pantryIng : pantry) {
            if (pantryIng.equals(item)) {
                quantityAvail = pantryIng.quantity;
                break;
            }
        }
        holder.itemNameTextView.setText(item.name);
        holder.quantityAvailTextView.setText(String.valueOf(quantityAvail));
        holder.quantityNeededTextView.setText(String.valueOf(item.quantity));

        int status = ContextCompat.getColor(this.context, (quantityAvail >= item.quantity) ? R.color.green : R.color.red);
        holder.ingredientStatus.setCardBackgroundColor(status);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
