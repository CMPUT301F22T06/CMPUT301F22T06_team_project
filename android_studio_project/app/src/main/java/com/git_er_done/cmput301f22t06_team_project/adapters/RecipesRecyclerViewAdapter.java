package com.git_er_done.cmput301f22t06_team_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.Comparator;
import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.interfaces.RecipesRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder>{

    private final RecipesRecyclerViewInterface rvInterface;
    private List<Recipe> mRecipes;

    View recipeView;

    /**
     * Generic constructor for this adapter
     * @param rvInterface - Reference to an interface for handling onItemLongClick events
     */
    public RecipesRecyclerViewAdapter(RecipesRecyclerViewInterface rvInterface) {
        mRecipes = new ArrayList<Recipe>();
        this.rvInterface = rvInterface;
    }

    /**
     * Inflates item layout and creates view holder
     * A viewHolder describes an item and metadata about its place within the RecylerView
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
        recipeView = inflater.inflate(R.layout.recipe_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(recipeView);

        //Drawable d = ContextCompat.getDrawable(context, R.drawable.white_background);
        //recipeView.setBackground(d);

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
        TextView description = holder.commentTextView;
        TextView category = holder.categoryTextView;
        TextView preptime = holder.preptimeTextView;
        TextView amount = holder.servingsTextView;

        name.setText(recipe.getTitle());
        description.setText(recipe.getComments());
        category.setText(recipe.getCategory());
        preptime.setText(String.valueOf(recipe.getPrep_time()));
        amount.setText(String.valueOf(recipe.getServings()));

        //ArrayList<String> ingredientNames = new ArrayList<>();
        for(RecipeIngredient i: recipe.getIngredients()){
//            ingredientNames.add(i.getName());
            //unit.setText(Log.d(TAG, i.getName()));


            // MISSING - PHOTOGRAPH
        }
    }

    /**
     * Determine the number of items (recipe instances) in list
     *
     * @return Integer - The number of items in the list
     */
    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    /**
     * Return an Ingredient instance which exists in the provided list position
     * @param position - The position of the item within the recipe list
     * @return
     */
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
        ProgressBar progressBar;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBarId);
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

    public ArrayList<Recipe> getRecipesList(){
        return (ArrayList<Recipe>) mRecipes;
    }

    public void deleteRecipe(int position){
        mRecipes.remove(position);
        notifyDataSetChanged();
    }

    public void fakeDeleteForUndo(int position){
        rvInterface.onItemDeleted(mRecipes.get(position), position);
    }

    public void addRecipe(Recipe newRecipe){
        mRecipes.add(newRecipe);
        notifyDataSetChanged();
    }

    public void modifyRecipe(Recipe recipe, int position){
        mRecipes.set(position, recipe);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void sortByTitle(){
        Collections.sort(mRecipes, new Comparator<Recipe>(){
            @Override
            public int compare(Recipe lhs, Recipe rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
        notifyDataSetChanged();
    }
    public void sortByCategory(){
        Collections.sort(mRecipes, new Comparator<Recipe>(){
            @Override
            public int compare(Recipe lhs, Recipe rhs) {
                return lhs.getCategory().compareTo(rhs.getCategory());
            }
        });
        notifyDataSetChanged();
    }
    public void sortByServings(){
        Collections.sort(mRecipes, new Comparator<Recipe>(){
            @Override
            public int compare(Recipe lhs, Recipe rhs) {
                return lhs.getServings().compareTo(rhs.getServings());
            }
        });
        notifyDataSetChanged();
    }
    public void sortByPrepTime(){
        Collections.sort(mRecipes, new Comparator<Recipe>(){
            @Override
            public int compare(Recipe lhs, Recipe rhs) {
                return lhs.getPrep_time().compareTo(rhs.getPrep_time());
            }
        });
        notifyDataSetChanged();
    }


}



