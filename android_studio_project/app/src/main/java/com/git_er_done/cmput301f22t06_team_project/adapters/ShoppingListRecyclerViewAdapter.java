package com.git_er_done.cmput301f22t06_team_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.interfaces.ShoppingListRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.shoppingList.ShoppingListIngredient;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder>{

    private final ShoppingListRecyclerViewInterface rvInterface;
    private List<Ingredient> mShoppingList;

    View shoppingListView;

    public ShoppingListRecyclerViewAdapter(ArrayList<Ingredient> testShoppingList, ShoppingListRecyclerViewInterface rvInterface) {
        mShoppingList = testShoppingList;
        this.rvInterface = rvInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        shoppingListView = inflater.inflate(R.layout.shopping_list_item, parent, false);

        // Return a new holder instance
        ShoppingListRecyclerViewAdapter.ViewHolder viewHolder = new ShoppingListRecyclerViewAdapter.ViewHolder(shoppingListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the object instance based on position in recyclerView
        Ingredient shoppingListIngredient = mShoppingList.get(position);

        TextView name = holder.nameTextView;
        TextView description = holder.descriptionTextView;
        TextView category = holder.categoryTextView;
        TextView amount = holder.amountTextView;
        TextView unit = holder.unitTextView;

        name.setText(shoppingListIngredient.getName());
        description.setText(shoppingListIngredient.getDesc());
        category.setText(shoppingListIngredient.getCategory());
        amount.setText(shoppingListIngredient.getAmount().toString());
        unit.setText(shoppingListIngredient.getUnit());
    }

    @Override
    public int getItemCount() {
        return mShoppingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView amountTextView;
        public TextView unitTextView;
        public TextView categoryTextView;

        //Constructor accepts entire item row and does view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores itemView in a public final member variable that can be used to access context from any ViewHolder instance
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv_shopping_list_item_name);
            descriptionTextView = itemView.findViewById(R.id.tv_shopping_list_item_description);
            categoryTextView = itemView.findViewById(R.id.tv_shopping_list_item_category);
            amountTextView = itemView.findViewById(R.id.tv_shopping_list_item_amount);
            unitTextView = itemView.findViewById(R.id.tv_shopping_list_item_unit);

        }
    }
}
