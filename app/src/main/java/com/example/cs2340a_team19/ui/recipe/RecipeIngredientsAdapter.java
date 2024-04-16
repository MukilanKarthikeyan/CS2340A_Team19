package com.example.cs2340a_team19.ui.recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.models.Ingredient;

import java.util.List;

public class RecipeIngredientsAdapter
        extends RecyclerView.Adapter<RecipeIngredientsAdapter.ViewHolder> {
    private List<Ingredient> itemList;
    private List<Ingredient> pantry;

    private Context context;

    public RecipeIngredientsAdapter(List<Ingredient> itemList,
                                    List<Ingredient> pantry, Context context) {
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
    public void onBindViewHolder(final ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        final Ingredient item = itemList.get(position);
        int quantity = 0;
        for (Ingredient pantryIng : pantry) {
            Log.d("GryphDebug", "Pantry Ing: " + (
                    (pantryIng != null) ? pantryIng.getName() : "NULL"));
            if (pantryIng.equals(item)) {
                quantity = pantryIng.getQuantity();
                break;
            }
        }
        holder.itemNameTextView.setText(item.getName());
        holder.quantityAvailTextView.setText(String.valueOf(quantity));
        holder.quantityNeededTextView.setText(String.valueOf(item.getQuantity()));


        int recipeStatus = ContextCompat.getColor(this.context, (
                quantity >= item.getQuantity()) ? R.color.green : R.color.red);
        holder.ingredientStatus.setCardBackgroundColor(recipeStatus);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
