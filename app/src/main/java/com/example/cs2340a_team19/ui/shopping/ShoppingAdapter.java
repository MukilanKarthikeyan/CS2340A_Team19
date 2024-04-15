//package com.example.cs2340a_team19.ui.shopping;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.cs2340a_team19.R;
//
//import com.example.cs2340a_team19.ui.recipe.RecipeAdapter;
//
//import org.w3c.dom.Text;
//
//public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>{
//    private List<ShopItem> shoppingList;
//
//    class ShoppingViewHolder extends RecyclerView.ViewHolder {
//        public TextView shopItemLabel;
//        public TextView shopItemQuant;
//
//        public ShoppingViewHolder(View view) {
//            super(view);
//            shopItemLabel = view.findViewById(R.id.shopping_item_label);
//            shopItemQuant = view.findViewById(R.id.shopping_item_quantity);
//        }
//    }
//
//    public ShoppingAdapter(List<ShopItem> itemList) {
//        this.shoppingList = itemList;
//    }
//
//    @Override
//    public ShoppingViewHolder onCreatViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, flase);
//        return new ShoppingAdapter().ShoppingViewHolder(itemView);
//    }
//
//    @NonNull
//    @Override
//    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(final ShoppingViewHolder holder, @SurpressLint("RecyclerView") final int posisiton) {
//        final ShopItem item = shoppingList.get(position);
//        holder.shopItemLabel.setText(item.name);
//        holder.shopItemQuant.setText(item.quanity);
//    }
//    @Override
//    public int getItemCount() {
//        return shoppingList.size();
//    }
//
//
//
//}
