package com.git_er_done.cmput301f22t06_team_project.controllers;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.RecipesRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;

import java.util.List;

public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder>{

    private final RecipesRecyclerViewInterface rvInterface;
    private List<Recipe> mRecipes;

    public RecipesRecyclerViewAdapter(List<Recipe> recipes, RecipesRecyclerViewInterface rvInterface) {
        mRecipes = recipes;
        this.rvInterface = rvInterface;
    }

    /**
     * Inflates item layout and creates view holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override

    public RecipesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recipe_list_item, parent, false);

        // Return a new holder instance
        RecipesRecyclerViewAdapter.ViewHolder viewHolder = new RecipesRecyclerViewAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    /**
     * Set the view attributes based on associated instance data
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecipesRecyclerViewAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Recipe recipe = mRecipes.get(position);

        // Set item views based on your views and data model
        TextView name = holder.nameTextView;
        name.setText(recipe.getTitle());

        TextView description = holder.commentTextView;
        description.setText(recipe.getComments());

        TextView location = holder.categoryTextView;
        location.setText(recipe.getCategory());

        TextView bestBeforeDate = holder.preptimeTextView;
        bestBeforeDate.setText(String.valueOf(recipe.getPrep_time()));

        TextView amount = holder.servingsTextView;
        amount.setText(String.valueOf(recipe.getServings()));

        TextView unit = holder.recipeIngredientsTextView;
        //ArrayList<String> ingredientNames = new ArrayList<>();
        for(RecipeIngredient i: recipe.getIngredients()){
//            ingredientNames.add(i.getName());
            unit.setText(Log.d(TAG, i.getName()));


            // MISSING - PHOTOGRAPH
        }
    }

    /**
     * Determine the number of items (recipe instances) in list
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public Recipe getItem(int position) {
        return mRecipes.get(position);
    }

    // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView commentTextView;
        public TextView categoryTextView;
        public TextView preptimeTextView;
        public TextView servingsTextView;
        public TextView recipeIngredientsTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv_recipe_list_item_name);
            commentTextView = itemView.findViewById(R.id.tv_recipe_list_item_comment);
            categoryTextView = itemView.findViewById(R.id.tv_recipe_list_item_category);
            preptimeTextView = itemView.findViewById(R.id.tv_recipe_list_item_preptime);
            servingsTextView = itemView.findViewById(R.id.tv_recipe_list_item_servings);
            //recipeIngredientsTextView = itemView.findViewById(R.id.tv_recipe_list_item_recipe_ingredients);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //check rv interface is not null
                    if (rvInterface != null) {
                        //get position from adapter for onclickmethod
                        int pos = getAdapterPosition();

                        //ensure position is valid
                        if (pos != RecyclerView.NO_POSITION) {
                            rvInterface.onItemLongClick(pos);
                            return true;
                        }

                    }
                    return false;
                }
            });
        }

    }
}



