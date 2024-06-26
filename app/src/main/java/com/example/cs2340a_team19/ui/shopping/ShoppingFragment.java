package com.example.cs2340a_team19.ui.shopping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cs2340a_team19.R;

import com.example.cs2340a_team19.databinding.FragmentShoppingBinding;
import com.example.cs2340a_team19.models.Ingredient;

import java.util.List;

public class ShoppingFragment extends Fragment {

    private FragmentShoppingBinding binding;
    private ShoppingViewModel vm = null;
    private Button addShopItem;
    private Button buyShopItems;

    private EditText shopItemName;
    private EditText shopItemQuant;
    private EditText shopItemCalorie;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShoppingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        shopItemName = view.findViewById(R.id.shop_add_item_name);
        shopItemQuant = view.findViewById(R.id.shop_add_item_quant);
        shopItemCalorie = view.findViewById(R.id.shop_add_item_calorie);
        addShopItem = view.findViewById(R.id.add_shop_item_button);
        buyShopItems = view.findViewById(R.id.buy_shop_items_button);

        this.recyclerView = view.findViewById(R.id.recycler_shopping_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        this.vm = new ShoppingViewModel(this);

        addShopItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = shopItemName.getText().toString();
                String itemQuant = shopItemQuant.getText().toString();
                String itemCalorie = shopItemCalorie.getText().toString();

                if (itemName.isEmpty() || itemQuant.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter something",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int intItemQuant = 0;
                try {
                    intItemQuant = Integer.parseInt(itemQuant);
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getContext(), "Please enter a valid quantity",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (intItemQuant <= 0) {
                    Toast.makeText(getContext(), "Please enter a quantity above 0",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int intItemCalorie = 0;
                try {
                    intItemCalorie = Integer.parseInt(itemCalorie);
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getContext(), "Please enter a valid Calorie Amount",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (intItemCalorie <= 0) {
                    Toast.makeText(getContext(), "Please enter a valid Calorie Amount",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                vm.addIngredient(new Ingredient(itemName, intItemCalorie, intItemQuant));
                Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
            }
        });

        buyShopItems.setOnClickListener((v) -> {
            if (recyclerView.getAdapter() instanceof ShoppingAdapter
                    && recyclerView.getAdapter() != null) {
                ((ShoppingAdapter) recyclerView.getAdapter()).buyItems();
            } else  {
                Log.d("UI_ERROR", "Recycler View Adapter was null"
                        + " or not a Shopping Adapter");
            }

        });
    }

    public void updateUI(List<Ingredient> shoppingList, ShoppingViewModel vm) {
        recyclerView.setAdapter(new ShoppingAdapter(shoppingList, vm));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}