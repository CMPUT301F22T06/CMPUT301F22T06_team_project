package com.git_er_done.cmput301f22t06_team_project.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder> {

    private List<Ingredient> mIngredients;

    public IngredientsRecyclerViewAdapter(List<Ingredient> ingredients){
        mIngredients = ingredients;
    }

    /**
     * Inflates item layout and creates view holder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.ingredient_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    /**
     * Set the view attributes based on associated instance data
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Ingredient ingredient = mIngredients.get(position);

        // Set item views based on your views and data model
        TextView name = holder.nameTextView;
        name.setText(ingredient.getName());

        TextView description = holder.descriptionTextView;
        description.setText(ingredient.getDesc());
    }

    /**
     * Determine the number of items (ingredient instances) in list
     * @return
     */
    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView descriptionTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.tv_ingredient_list_item_name);
            descriptionTextView = itemView.findViewById(R.id.tv_ingredient_list_item_description);
           }
    }
}
