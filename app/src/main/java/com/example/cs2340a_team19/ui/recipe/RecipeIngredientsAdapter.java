package com.example.cs2340a_team19.ui.recipe;

import android.annotation.SuppressLint;
import android.content.Context;
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

    public RecipeIngredientsAdapter(List<Ingredient> itemList, List<Ingredient> pantry,
                                    Context context) {
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
        int quantityAvail = 0;
        for (Ingredient pantryIng : pantry) {
            if (pantryIng.equals(item)) {
                quantityAvail = pantryIng.getQuantity();
                break;
            }
        }
        holder.itemNameTextView.setText(item.getName());
        holder.quantityAvailTextView.setText(String.valueOf(quantityAvail));
        holder.quantityNeededTextView.setText(String.valueOf(item.getQuantity()));

        int status = ContextCompat.getColor(this.context, (quantityAvail >= item.getQuantity())
                ? R.color.green : R.color.red);
        holder.ingredientStatus.setCardBackgroundColor(status);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNameTextView;
        private TextView quantityAvailTextView;
        private TextView quantityNeededTextView;

        private CardView ingredientStatus;

        public ViewHolder(View view) {
            super(view);
            itemNameTextView = view.findViewById(R.id.ingredientName);
            quantityAvailTextView = view.findViewById(R.id.ingredient_quantity_available);
            quantityNeededTextView = view.findViewById(R.id.ingredient_quantity_needed);
            ingredientStatus = view.findViewById(R.id.recipe_ingredient_status);
        }

        public TextView getItemNameTextView() {
            return itemNameTextView;
        }

        public void setItemNameTextView(TextView itemNameTextView) {
            this.itemNameTextView = itemNameTextView;
        }

        public TextView getQuantityAvailTextView() {
            return quantityAvailTextView;
        }

        public void setQuantityAvailTextView(TextView quantityAvailTextView) {
            this.quantityAvailTextView = quantityAvailTextView;
        }

        public TextView getQuantityNeededTextView() {
            return quantityNeededTextView;
        }

        public void setQuantityNeededTextView(TextView quantityNeededTextView) {
            this.quantityNeededTextView = quantityNeededTextView;
        }

        public CardView getIngredientStatus() {
            return ingredientStatus;
        }

        public void setIngredientStatus(CardView ingredientStatus) {
            this.ingredientStatus = ingredientStatus;
        }
    }
}
