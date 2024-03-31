package com.example.cs2340a_team19.ui.ingredients;

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

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Ingredient> itemList;

    private DatabaseHandler dbHandler;
    private PantryHandler pantryHandler;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView quantityTextView;
        public Button plusButton;
        public Button minusButton;

        public ViewHolder(View view) {
            super(view);
            itemNameTextView = view.findViewById(R.id.ingredientName);
            quantityTextView = view.findViewById(R.id.ingredientQuantity);
            plusButton = view.findViewById(R.id.buttonPlus);
            minusButton = view.findViewById(R.id.buttonMinus);
        }
    }

    public IngredientsAdapter(List<Ingredient> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false);

        this.dbHandler = DatabaseHandler.getInstance();
        this.pantryHandler = dbHandler.getPantryHandler();
        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            // Add event listeners here!
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile because dbHandler Initialization Failed!");
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Ingredient item = itemList.get(position);
        holder.itemNameTextView.setText(item.name);
        holder.quantityTextView.setText(String.valueOf(item.quantity));

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increase quantity
                pantryHandler.updateIngredientQuantity(dbHandler.getUserID(), item.ingredientID, item.quantity + 1);
                notifyItemChanged(position);
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrease quantity if greater than 0
                if (item.quantity > 1) {
                    pantryHandler.updateIngredientQuantity(dbHandler.getUserID(), item.ingredientID, item.quantity + 1);
                    notifyItemChanged(position);
                } else {
                    pantryHandler.removeIngredient(dbHandler.getUserID(), item.ingredientID);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
