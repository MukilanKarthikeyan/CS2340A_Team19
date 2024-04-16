package com.example.cs2340a_team19.ui.recipe;

//import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;


import static java.security.AccessController.getContext;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList;
    private List<Ingredient> pantry;
    private Context context;

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        public Recipe currItem;
        public TextView recipeNameLabel;
        public CardView recipePantryStatus;
        public ImageView expandIndicator;
        public CardView layout;
        public TextView recipieDescription;
        public RecyclerView ingredientsList;

        public TextView statusActionText;
        private boolean expanded = false;

        public TextView getRecipeNameLabel() {
            return recipeNameLabel;
        }

        public void setRecipeNameLabel(TextView recipeNameLabel) {
            this.recipeNameLabel = recipeNameLabel;
        }

        public RecyclerView getIngredientsList() {
            return ingredientsList;
        }

        public void setIngredientsList(RecyclerView ingredientsList) {
            this.ingredientsList = ingredientsList;
        }

        public CardView getRecipePantryStatus() {
            return recipePantryStatus;
        }

        public void setRecipePantryStatus(CardView recipePantryStatus) {
            this.recipePantryStatus = recipePantryStatus;
        }

        public Recipe getCurrItem() {
            return currItem;
        }

        public void setCurrItem(Recipe currItem) {
            this.currItem = currItem;
        }

        public RecipeViewHolder(View view) {
            super(view);
            recipeNameLabel = view.findViewById(R.id.recipe_name);

            ingredientsList = view.findViewById(R.id.recipe_ingredient_list);

            recipePantryStatus = view.findViewById(R.id.pantry_status_indicator);
            statusActionText = view.findViewById(R.id.status_action_text);

            recipieDescription = view.findViewById(R.id.recipe_description);

            layout = view.findViewById(R.id.recipe_card);
            expandIndicator = view.findViewById(R.id.card_expand_indicator);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: this is probably breaking som sort of design rule -> fix. we make this once curritem is actually instanciated
                    ingredientsList.setLayoutManager(new LinearLayoutManager(context));
                    ingredientsList.setAdapter(new RecipeIngredientsAdapter(currItem.getIngredients(), pantry, context));

                    //TODO: convert the following codeblock into an if-else block
                    expanded = !expanded;
                    int vis = (ingredientsList.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                    int indicator = (vis == View.GONE) ? R.drawable.baseline_expand_more_24 : R.drawable.baseline_expand_less_24;
                    int statusCardSize = (vis == View.GONE) ? 30 : 100; // specified in dp
                    statusCardSize *= context.getResources().getDisplayMetrics().density; // accomodates for the current density factor

                    final AutoTransition transition = new AutoTransition();


                    //Change duration of animation here:
                    transition.setDuration(0L);
                    TransitionManager.beginDelayedTransition(layout, transition);
                    //TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                    ingredientsList.setVisibility(vis);
                    recipieDescription.setVisibility(vis);
                    statusActionText.setVisibility(vis);

                    expandIndicator.setImageResource(indicator);

                    ViewGroup.LayoutParams statusParams = recipePantryStatus.getLayoutParams();
                    statusParams.width = statusCardSize;
                    recipePantryStatus.setLayoutParams(statusParams);

                }
            });
            recipePantryStatus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!expanded) {
                        return;
                    }
                    //TODO: implement logic for what happens when you click the card to cook/buy
                    Log.d("Mukilan", "cook/buy button clicked");
                }
            });

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
        holder.recipeNameLabel.setText(item.getName());
        holder.statusActionText.setText((item.isPantryReady()) ? R.string.status_cook : R.string.status_buy);
        //TODO: the adapter is created mulitple times see line 76
        holder.ingredientsList.setAdapter(new RecipeIngredientsAdapter(item.getIngredients(), pantry, this.context));

        //TODO: figure out what size of the text looks good
        //holder.recipieDescription.setTextSize(10);
        holder.recipieDescription.setText(item.getDescription());

        int recipeStatus = ContextCompat.getColor(this.context, (item.isPantryReady()) ? R.color.green : R.color.red);
        holder.recipePantryStatus.setCardBackgroundColor(recipeStatus);

    }
    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
