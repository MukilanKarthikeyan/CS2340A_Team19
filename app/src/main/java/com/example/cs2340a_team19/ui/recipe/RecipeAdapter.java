package com.example.cs2340a_team19.ui.recipe;

//import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.ui.contextmenu.Item;
import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.PantryHandler;
import com.example.cs2340a_team19.models.Recipe;
import com.example.cs2340a_team19.ui.ingredients.IngredientsAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    private List<Recipe> recipeList;
    private List<Ingredient> pantry;
    private Context context;
    class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeNameLabel;
        public RecyclerView ingredientsList;

        public CardView recipePantryStatus;
        public Recipe currItem;
        private boolean displayIngredients = false;


        public RecipeViewHolder(View view) {
            super(view);
            recipeNameLabel = view.findViewById(R.id.recipe_name);

            ingredientsList = view.findViewById(R.id.recipe_ingredient_list);

            recipePantryStatus = view.findViewById(R.id.pantry_status_indicator);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (displayIngredients) {
                        ingredientsList.setLayoutManager(new LinearLayoutManager(context));
                        ingredientsList.setAdapter(new RecipeIngredientsAdapter(currItem.ingredients, pantry));
                    } else {
                        ingredientsList.setAdapter(new RecipeIngredientsAdapter(new ArrayList<>(), pantry));
                    }
                    displayIngredients = !displayIngredients;
                }
            });

            //quantitylabel = view.findViewById(R.id.ingredient_quantity);

        }

    }
    public RecipeAdapter(List<Recipe> itemList, List<Ingredient> pantry, Context context) {
        this.recipeList = itemList;
        this.pantry = pantry;
        this.context = context;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);
        return new RecipeAdapter.RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Recipe item = recipeList.get(position);
        holder.currItem = item;
        holder.recipeNameLabel.setText(item.name);

        int recipeStatus = ContextCompat.getColor(this.context, (item.pantryReady) ? R.color.green : R.color.red);
        holder.recipePantryStatus.setCardBackgroundColor(recipeStatus);

//        holder.ingredientsList.setLayoutManager(new LinearLayoutManager(context));
//        holder.ingredientsList.setAdapter(new RecipeIngredientsAdapter(item.ingredients));


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
