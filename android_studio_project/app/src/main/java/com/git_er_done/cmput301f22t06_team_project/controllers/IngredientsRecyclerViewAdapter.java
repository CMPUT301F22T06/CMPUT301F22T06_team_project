package com.git_er_done.cmput301f22t06_team_project.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.IngredientsRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Followed this tutorial https://guides.codepath.com/android/using-the-recyclerview
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views

/**
 * Adapter used to render each item in the recycler view dynamically
 * Constructor takes in a list of ingredients and an interface reference for handling onItemLongClick events
 */
public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder> {

    private final IngredientsRecyclerViewInterface rvInterface;
    private List<Ingredient> mIngredients;

    View ingredientView;

    /**
     * Generic constructor for this adapter
     * @param rvInterface - Reference to an interface for handling onItemLongClick events
     */
    public IngredientsRecyclerViewAdapter(IngredientsRecyclerViewInterface rvInterface){
        mIngredients = new ArrayList<Ingredient>();
        this.rvInterface = rvInterface;
    }

    /**
     * Inflates item layout, creates and returns ViewHolder.
     * A viewHolder describes an item and metadata about its place within the RecylerView
     * @param parent  ViewGroup
     * @param viewType Integer
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        ingredientView = inflater.inflate(R.layout.ingredient_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(ingredientView);
        return viewHolder;
    }


    /**
     * Set each item view attributes based on associated instance data
     * @param holder ViewHolder
     * @param position Integer - position within the RecyclerView List
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the object instance based on position in recyclerView
        Ingredient ingredient = mIngredients.get(position);

        TextView name = holder.nameTextView;
        TextView description = holder.descriptionTextView;
        TextView location = holder.locationTextView;
        TextView bestBeforeDate = holder.bestBeforeDateTextView;
        TextView category = holder.categoryTextView;
        TextView amount = holder.amountTextView;
        TextView unit = holder.unitTextView;

        name.setText(ingredient.getName());
        description.setText(ingredient.getDesc());
        location.setText(ingredient.getLocation());
        bestBeforeDate.setText(ingredient.getBestBefore().toString());
        category.setText(ingredient.getCategory());
        amount.setText(ingredient.getAmount().toString());
        unit.setText(ingredient.getUnit());
    }

    /**
     * Return the number of items (ingredient instances) in list
     * @return Integer - The number of items in the list
     */
    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    /**
     * Return an Ingredient instance which exists in the provided list position
     * @param position - The position of the item within the ingredient list
     * @return
     */
    public Ingredient getItem(int position) {
        return mIngredients.get(position);
    }

    // Direct reference to each of the views within a data item. Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView bestBeforeDateTextView;
        public TextView amountTextView;
        public TextView unitTextView;
        public TextView locationTextView;
        public TextView categoryTextView;

        //Constructor accepts entire item row and does view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores itemView in a public final member variable that can be used to access context from any ViewHolder instance
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv_ingredient_list_item_name);
            descriptionTextView = itemView.findViewById(R.id.tv_ingredient_list_item_description);
            locationTextView = itemView.findViewById(R.id.tv_ingredient_list_item_location);
            bestBeforeDateTextView = itemView.findViewById(R.id.tv_ingredient_list_item_best_before_date);
            categoryTextView = itemView.findViewById(R.id.tv_ingredient_list_item_category);
            amountTextView = itemView.findViewById(R.id.tv_ingredient_list_item_amount);
            unitTextView = itemView.findViewById(R.id.tv_ingredient_list_item_unit);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //check rv interface is not null
                    if(rvInterface != null){
                        int pos = getAdapterPosition();

                        //ensure pos long clicked is valid
                        if(pos != RecyclerView.NO_POSITION){
                            rvInterface.onItemLongClick(pos);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    public ArrayList<Ingredient> getIngredientsList(){
        return (ArrayList<Ingredient>) mIngredients;
    }

    public void deleteItem(int position){
        mIngredients.remove(position);
        notifyDataSetChanged();
    }

    public void fakeDeleteForUndo(int position){
        rvInterface.onItemDeleted(mIngredients.get(position), position);
    }

    public void addItem(Ingredient newIngredient){
        mIngredients.add(newIngredient);
        notifyDataSetChanged();
    }

    public void modifyIngredient(Ingredient ing, int position){
        mIngredients.set(position ,ing);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void sortByName(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByDescription(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getDesc().compareTo(rhs.getDesc());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByBestBeforeDate(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getBestBefore().compareTo(rhs.getBestBefore());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByLocation(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getLocation().compareTo(rhs.getLocation());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByCategory(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getCategory().compareTo(rhs.getCategory());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByAmount(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getAmount().compareTo(rhs.getAmount());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByUnit(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getUnit().compareTo(rhs.getUnit());
            }
        });
        notifyDataSetChanged();
    }

}
