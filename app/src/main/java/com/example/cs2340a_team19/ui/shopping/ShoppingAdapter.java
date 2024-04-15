package com.example.cs2340a_team19.ui.shopping;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cs2340a_team19.R;

import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.ui.recipe.RecipeAdapter;

import org.w3c.dom.Text;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder> {
    private List<Ingredient> shoppingList;

    class ShoppingViewHolder extends RecyclerView.ViewHolder {
        public TextView shopItemLabel;
        public TextView shopItemQuant;

        public ShoppingViewHolder(View view) {
            super(view);
            shopItemLabel = view.findViewById(R.id.shopping_item_label);
            shopItemQuant = view.findViewById(R.id.shopping_item_quantity);
        }
    }

    public ShoppingAdapter(List<Ingredient> itemList) {
        this.shoppingList = itemList;
    }

    @Override
    public ShoppingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, false);
        return new ShoppingAdapter.ShoppingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShoppingViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Ingredient item = shoppingList.get(position);
        holder.shopItemLabel.setText(item.getName());
        holder.shopItemQuant.setText(item.getQuantity());
    }
    @Override
    public int getItemCount() {
        return shoppingList.size();
    }



}
