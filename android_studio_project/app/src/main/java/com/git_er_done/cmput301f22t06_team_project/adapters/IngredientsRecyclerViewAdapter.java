package com.git_er_done.cmput301f22t06_team_project.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.interfaces.IngredientsRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Followed this tutorial https://guides.codepath.com/android/using-the-recyclerview
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views

//TODO - add newly created ingredient to top of recyclerview after creation
/**
 * Adapter used to render each item in the recycler view dynamically
 * Constructor takes in a list of ingredients and an interface reference for handling onItemLongClick events
 */
public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder> {

    private final IngredientsRecyclerViewInterface rvInterface;
    private List<Ingredient> mIngredients = new ArrayList<>();
    View ingredientView;

    /**
     * Generic constructor for this adapter
     * @param rvInterface - Reference to an interface for handling onItemLongClick events
     */
    public IngredientsRecyclerViewAdapter(IngredientsRecyclerViewInterface rvInterface){
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
        return new ViewHolder(ingredientView);
    }


    /**
     * Set each item view attributes based on associated instance data
     * @param holder ViewHolder
     * @param position Integer - position within the RecyclerView List
     */
    @SuppressLint("ResourceAsColor")
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
        LinearLayout background =holder.background;

        name.setText(ingredient.getName());
        description.setText(ingredient.getDesc());
        location.setText(ingredient.getLocation());
        bestBeforeDate.setText(ingredient.getBestBefore().toString());
        category.setText(ingredient.getCategory());
        amount.setText(ingredient.getAmount().toString());
        unit.setText(ingredient.getUnit());
        ;
        if (ingredient.getAmount()==0) {
            ingredient.setColor(Color.RED);
        }
        if (ingredient.getColor()!=Color.RED){

            amount.setTextColor(R.color.light_blue);
            //amount.setTextSize(12);
    }
        else{
            amount.setTextColor(Color.RED);
           // amount.setTextSize(20);
        }
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
     * @return boolean
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
        public String color;
        public LinearLayout background;
        ProgressBar progressBar;

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
            background = itemView.findViewById(R.id.background);
            progressBar = itemView.findViewById(R.id.progressBarId);

            //set expired ingredients to 0 amount
            setExpiredIngredientsAmountToZero();

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
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

    /**
     * Creates a new array list of ingredients
     * @return - arraylist returns a new array of list of ingredients in mIngredients
     */
    public ArrayList<Ingredient> getIngredientsList(){
        return (ArrayList<Ingredient>) mIngredients;
    }

    /**
     * This function checks the date if its expired and if it is, it will change it to 0 and change the color.
     */
    public void setExpiredIngredientsAmountToZero() {
        LocalDate today = LocalDate.now();
        for (int i = 0; i < mIngredients.size(); i++) {
            Ingredient anIngredient = mIngredients.get(i);
            if (anIngredient.getBestBefore().compareTo(today) < 0) {
                Ingredient oldIngredient = anIngredient;
                anIngredient.setAmount(0);
                IngredientDBHelper.modifyIngredientInDB(anIngredient, oldIngredient, i);
            }
        }
    }

    /**
     * Delete an item from the array list of ingredients
     * @param position - the integer location of where the deleted item will be
     */
    public void deleteItem(int position){
        mIngredients.remove(position);
        notifyDataSetChanged();
    }

    /**
     *  Get's the position of the location of where the deleted item and does a 'fake' delete where it show a popup
     *  This way we can do a makeshift undo. The user would have to click the 'delete' on the popup to delete again.
     * @param position - integer position of where the deleted item will be.
     */
    public void fakeDeleteForUndo(int position){
        rvInterface.onItemDeleted(mIngredients.get(position), position);
    }

    /**
     *  Add a new item into the ingredient list
     * @param newIngredient - the new ingredient you want to add into the new list. Of type ingredient.
     */
    public void addItem(Ingredient newIngredient){
        mIngredients.add(newIngredient);
        notifyDataSetChanged();
    }

    /**
     * update the ingredient in the list at the position to a new value
     * @param ing - the ingredient that is to be modified. Of type ingredient
     * @param position - the integer position of where the ingredient is.
     */
    public void modifyIngredient(Ingredient ing, int position){
        mIngredients.set(position ,ing);
        notifyDataSetChanged();
    }

    /**
     * This will sort all the ingredients in the recycler view by name (a-z)
     */
    public void sortIngredientByName(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        notifyDataSetChanged();
    }

    /**
     * This will sort all the ingredients in the recycler view by the description (alphabetical)
     */
    public void sortIngredientByDescription(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getDesc().compareTo(rhs.getDesc());
            }
        });
        notifyDataSetChanged();
    }

    /**
     * This will sort all the ingredients in the recycler view by the best before date (oldest to newest)
     */
    public void sortIngredientByBestBeforeDate(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getBestBefore().compareTo(rhs.getBestBefore());
            }
        });
        notifyDataSetChanged();
    }

    /**
     * This will sort all the ingredients in the recycler view by the location (alphabetical)
     */
    public void sortIngredientByLocation(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getLocation().compareTo(rhs.getLocation());
            }
        });
        notifyDataSetChanged();
    }

    /**
     * This will sort all the ingredients in the recycler view by the category (alphabetical)
     */
    public void sortIngredientByCategory(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getCategory().compareTo(rhs.getCategory());
            }
        });
        notifyDataSetChanged();
    }

    /**
     * This will sort all the ingredients in the recycler view by the amount (lowest to highest)
     */
    public void sortIngredientByAmount(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getAmount().compareTo(rhs.getAmount());
            }
        });
        notifyDataSetChanged();
    }

    /**
     * This will sort all the ingredients in the recycler view by the unit (alphabetical)
     */
    public void sortIngredientByUnit(){
        Collections.sort(mIngredients, new Comparator<Ingredient>(){
            @Override
            public int compare(Ingredient lhs, Ingredient rhs) {
                return lhs.getUnit().compareTo(rhs.getUnit());
            }
        });
        notifyDataSetChanged();
    }

}
