
package com.example.cs2340a_team19.ui.shopping;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.cs2340a_team19.R;

import com.example.cs2340a_team19.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder> {
    private List<Ingredient> shoppingList;
    private List<Boolean> isChecked;
    private ShoppingViewModel vm;

    public ShoppingAdapter(List<Ingredient> itemList, ShoppingViewModel vm) {
        this.vm = vm;
        this.shoppingList = itemList;
        this.isChecked = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            isChecked.add(false);
        }
    }

    @Override
    public ShoppingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                shopping_list_item, parent, false);
        return new ShoppingAdapter.ShoppingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShoppingViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        final Ingredient item = shoppingList.get(position);
        holder.shopItemLabel.setText(item.getName());
        holder.shopItemQuant.setText(String.valueOf(item.getQuantity()));
        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.decrementIngredientQuantity(position);
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.incrementIngredientQuantity(position);
            }
        });

        holder.checkbox.setOnClickListener((v) -> {
            isChecked.set(position, !isChecked.get(position));
        });
    }
    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public void buyItems() {
        List<Ingredient> toBuy = new ArrayList<>();
        for (int i = 0; i < isChecked.size(); i++) {
            if (isChecked.get(i)) {
                toBuy.add(new Ingredient(shoppingList.get(i)));
            }
        }
        vm.buy(toBuy);
    }

    class ShoppingViewHolder extends RecyclerView.ViewHolder {
        private TextView shopItemLabel;
        private TextView shopItemQuant;
        private ImageView plusButton;
        private ImageView minusButton;
        private CheckBox checkbox;

        public ShoppingViewHolder(View view) {
            super(view);
            checkbox = view.findViewById(R.id.shopping_item_label);
            shopItemLabel = view.findViewById(R.id.shopping_item_label);
            shopItemQuant = view.findViewById(R.id.shopping_item_quantity);
            plusButton = view.findViewById(R.id.buttonPlus);
            minusButton = view.findViewById(R.id.buttonMinus);
        }

        public TextView getShopItemLabel() {
            return shopItemLabel;
        }

        public void setShopItemLabel(TextView shopItemLabel) {
            this.shopItemLabel = shopItemLabel;
        }

        public TextView getShopItemQuant() {
            return shopItemQuant;
        }

        public void setShopItemQuant(TextView shopItemQuant) {
            this.shopItemQuant = shopItemQuant;
        }

        public ImageView getPlusButton() {
            return plusButton;
        }

        public void setPlusButton(ImageView plusButton) {
            this.plusButton = plusButton;
        }

        public ImageView getMinusButton() {
            return minusButton;
        }

        public void setMinusButton(ImageView minusButton) {
            this.minusButton = minusButton;
        }

        public CheckBox getCheckbox() {
            return checkbox;
        }

        public void setCheckbox(CheckBox checkbox) {
            this.checkbox = checkbox;
        }
    }
}