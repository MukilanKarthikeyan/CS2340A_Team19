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

    public IngredientsAdapter(List<Ingredient> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ALEX", "ingredients list adapter onCreateViewHolder check");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false);

        this.dbHandler = DatabaseHandler.getInstance();
        this.pantryHandler = dbHandler.getPantryHandler();
        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            Log.d("ALEX", "ingredients list adapter onCreateViewHolder dbHandler initialized");
            // Add event listeners here!
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile, "
                    + "because dbHandler Initialization Failed!");
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        final Ingredient item = itemList.get(position);
        holder.itemNameTextView.setText(item.getName());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
        Log.d("ALEX", "ingredients list adapter onBindViewHolder check");
        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increase quantity
                pantryHandler.updateIngredientQuantity(dbHandler.getUserID(),
                        item.getIngredientID(), item.getQuantity() + 1);
                notifyItemChanged(position);
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrease quantity if greater than 0
                if (item.getQuantity() > 1) {
                    pantryHandler.updateIngredientQuantity(dbHandler.getUserID(),
                            item.getIngredientID(), item.getQuantity() - 1);
                    notifyItemChanged(position);
                } else {
                    pantryHandler.removeIngredient(dbHandler.getUserID(), item.getIngredientID());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNameTextView;
        private TextView quantityTextView;
        private Button plusButton;
        private Button minusButton;

        public ViewHolder(View view) {
            super(view);
            itemNameTextView = view.findViewById(R.id.ingredientName);
            quantityTextView = view.findViewById(R.id.ingredientQuantity);
            plusButton = view.findViewById(R.id.buttonPlus);
            minusButton = view.findViewById(R.id.buttonMinus);
        }

        public TextView getItemNameTextView() {
            return itemNameTextView;
        }

        public void setItemNameTextView(TextView itemNameTextView) {
            this.itemNameTextView = itemNameTextView;
        }

        public TextView getQuantityTextView() {
            return quantityTextView;
        }

        public void setQuantityTextView(TextView quantityTextView) {
            this.quantityTextView = quantityTextView;
        }

        public Button getPlusButton() {
            return plusButton;
        }

        public void setPlusButton(Button plusButton) {
            this.plusButton = plusButton;
        }

        public Button getMinusButton() {
            return minusButton;
        }

        public void setMinusButton(Button minusButton) {
            this.minusButton = minusButton;
        }
    }
}
