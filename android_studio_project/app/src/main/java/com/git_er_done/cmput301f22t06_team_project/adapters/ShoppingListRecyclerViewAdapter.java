package com.git_er_done.cmput301f22t06_team_project.adapters;

import static com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper.getIndexOfIngredientFromName;
import static com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper.modifyIngredientInDB;
import static com.git_er_done.cmput301f22t06_team_project.models.shoppingList.ShoppingListIngredient.testShoppingList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<ShoppingListIngredient> mShoppingList;

    View shoppingListView;

    public ShoppingListRecyclerViewAdapter(ArrayList<ShoppingListIngredient> testShoppingList, ShoppingListRecyclerViewInterface rvInterface) {
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
        ShoppingListIngredient shoppingListIngredient = mShoppingList.get(position);

        TextView name = holder.nameTextView;
        TextView description = holder.descriptionTextView;
        TextView category = holder.categoryTextView;
        TextView amount = holder.amountTextView;
        TextView unit = holder.unitTextView;
        Button gotButton = holder.gotButton;

        name.setText(shoppingListIngredient.getIngredient().getName());
        description.setText(shoppingListIngredient.getIngredient().getDesc());
        category.setText(shoppingListIngredient.getIngredient().getCategory());
        amount.setText(shoppingListIngredient.getIngredient().getAmount().toString());
        unit.setText(shoppingListIngredient.getIngredient().getUnit());

        gotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add testShoppingList.get(position) * QuantityNeeded to ingredients
                // by Ingredient.setamount = amount + quantitybought
                Ingredient selectedIngredient = testShoppingList.get(holder.getAdapterPosition()).getIngredient();
                int quantity = testShoppingList.get(holder.getAdapterPosition()).getRequiredAmount();
                testShoppingList.get(holder.getAdapterPosition()).getIngredient().setAmount(selectedIngredient.getAmount() + quantity);
                int pos = getIndexOfIngredientFromName(selectedIngredient.getName());
                Ingredient newIngredient = new Ingredient(selectedIngredient.getName(), selectedIngredient.getDesc(), selectedIngredient.getBestBefore(),
                        selectedIngredient.getLocation(), selectedIngredient.getUnit(), selectedIngredient.getCategory(),
                        selectedIngredient.getAmount() + quantity);
                modifyIngredientInDB(newIngredient, selectedIngredient, pos);
                Toast.makeText(v.getContext(), "Make sure to update ingredient details!", Toast.LENGTH_LONG).show();
            }
        });
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
        public Button gotButton;

        //Constructor accepts entire item row and does view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores itemView in a public final member variable that can be used to access context from any ViewHolder instance
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv_shopping_list_item_name);
            descriptionTextView = itemView.findViewById(R.id.tv_shopping_list_item_description);
            categoryTextView = itemView.findViewById(R.id.tv_shopping_list_item_category);
            amountTextView = itemView.findViewById(R.id.tv_shopping_list_item_amount);
            unitTextView = itemView.findViewById(R.id.tv_shopping_list_item_unit);
            gotButton = itemView.findViewById(R.id.got_shopping_list_item_button);

        }
    }
}
