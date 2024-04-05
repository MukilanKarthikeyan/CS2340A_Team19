package com.example.cs2340a_team19.ui.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cs2340a_team19.R;

import com.example.cs2340a_team19.databinding.FragmentShoppingBinding;

public class ShoppingFragment extends Fragment {

    private FragmentShoppingBinding binding;
    private Button addShopItem;
    private Button buyShopItems;

    private EditText shopItemName;
    private EditText shopItemQuant;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShoppingViewModel shoppingViewModel =
                new ViewModelProvider(this).get(ShoppingViewModel.class);

        binding = FragmentShoppingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        shopItemName = root.findViewById(R.id.shop_add_item_name);
        shopItemQuant = root.findViewById(R.id.shop_add_item_quant);
        addShopItem = root.findViewById(R.id.buy_shop_items_button);

        addShopItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = shopItemName.getText().toString();
                String itemQuant = shopItemQuant.getText().toString();

                if (itemName.isEmpty() || itemQuant.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter something", Toast.LENGTH_SHORT).show();
                }
                //TODO deal with the units of the quanitity


                shoppingViewModel.addItem(itemName, itemQuant);
                Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
            }
        });



        //shoppingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(View view @NonNull Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_shopping_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ShoppingViewModel viewModel = new ShoppingViewModel((shoppingList, pantry) -> recyclerView.setAdapter(new ShoppingAdapter(shoppingList)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}