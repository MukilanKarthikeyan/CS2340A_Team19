package com.example.cs2340a_team19.ui.recipe;

import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.PantryHandler;
import com.example.cs2340a_team19.models.Recipe;
import com.example.cs2340a_team19.ui.ingredients.IngredientsAdapter;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    private List<Recipe> recipeList;

    private DatabaseHandler dbHandler;
    private PantryHandler pantryHandler;
    class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeNameLabel;
        public CardView recipePantryStatus;

        public RecipeViewHolder(View view) {
            super(view);
            recipeNameLabel = view.findViewById(R.id.recipe_name);
            recipePantryStatus = view.findViewById(R.id.pantry_status_indicator);

            //quantitylabel = view.findViewById(R.id.ingredient_quantity);

        }

    }
    public RecipeAdapter(List<Recipe> itemList) {
        this.recipeList = itemList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);

        this.dbHandler = DatabaseHandler.getInstance();
        this.pantryHandler = dbHandler.getPantryHandler();
        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            // Add event listeners here!
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile because dbHandler Initialization Failed!");
        }
        return new RecipeAdapter.RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Recipe item = recipeList.get(position);
//        Log.d("GRYPHON_FINAL", item == null ? "NULL" : item.name);
        holder.recipeNameLabel.setText(item.name);
        holder.recipePantryStatus.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
        //holder.quantityTextView.setText(String.valueOf(item.quantity));

//        holder.minusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Decrease quantity if greater than 0
//                if (item.quantity > 1) {
//                    pantryHandler.updateIngredientQuantity(dbHandler.getUserID(), item.ingredientID, item.quantity - 1);
//                    notifyItemChanged(position);
//                } else {
//                    pantryHandler.removeIngredient(dbHandler.getUserID(), item.ingredientID);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

}
