package com.git_er_done.cmput301f22t06_team_project.adapters;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.fragments.IngredientAddEditDialogFragment;
import com.git_er_done.cmput301f22t06_team_project.fragments.IngredientsFragment;
import com.git_er_done.cmput301f22t06_team_project.fragments.RecipeAddEditDialogFragment;
import com.git_er_done.cmput301f22t06_team_project.fragments.ShoppingListFragment;
import com.git_er_done.cmput301f22t06_team_project.interfaces.IngredientsRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.interfaces.ShoppingListRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder>{

    private final ShoppingListRecyclerViewInterface rvInterface;
    private List<Ingredient> mShoppingList;
    FragmentActivity fragment;
    View shoppingListView;

    public ShoppingListRecyclerViewAdapter(ArrayList<Ingredient> testShoppingList, ShoppingListRecyclerViewInterface rvInterface) {
        mShoppingList = testShoppingList;
        this.rvInterface = rvInterface;
    }

    public ShoppingListRecyclerViewAdapter(ArrayList<Ingredient> testShoppingList, ShoppingListRecyclerViewInterface rvInterface, FragmentActivity fragment) {
        mShoppingList = testShoppingList;
        this.rvInterface = rvInterface;
        this.fragment = fragment;
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
        FloatingActionButton addToStorage = holder.addToIngredientStorage;

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
        public FloatingActionButton addToIngredientStorage;

        //Constructor accepts entire item row and does view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores itemView in a public final member variable that can be used to access context from any ViewHolder instance
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv_shopping_list_item_name);
            descriptionTextView = itemView.findViewById(R.id.tv_shopping_list_item_description);
            categoryTextView = itemView.findViewById(R.id.tv_shopping_list_item_category);
            amountTextView = itemView.findViewById(R.id.tv_shopping_list_item_amount);
            unitTextView = itemView.findViewById(R.id.tv_shopping_list_item_unit);
            addToIngredientStorage = itemView.findViewById(R.id.fab_shopping_add);

            addToIngredientStorage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rvInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            ArrayList<Ingredient> ingredientStorage = IngredientDBHelper.getIngredientsFromStorage();
                            Ingredient shoppingListIngredient = mShoppingList.get(pos);
                            int index = ingredientStorage.indexOf(shoppingListIngredient);
                            Ingredient ingredientFromStorage = ingredientStorage.get(index);

                            IngredientsRecyclerViewAdapter ivAdapter = new IngredientsRecyclerViewAdapter(new IngredientsRecyclerViewInterface() {
                                @Override
                                public void onItemLongClick(int position) {

                                }

                                @Override
                                public void onItemDeleted(Ingredient ing, int position) {

                                }
                            });
                            FragmentManager fm = fragment.getSupportFragmentManager();
                            IngredientAddEditDialogFragment editNameDialogFragment =
                                    IngredientAddEditDialogFragment.newInstance(
                                            ingredientFromStorage, shoppingListIngredient, ivAdapter);
                            editNameDialogFragment.show(fm, "fragment_ingredient_add_edit_dialog");
                        }
                    }
                }
            });
        }
    }
}
