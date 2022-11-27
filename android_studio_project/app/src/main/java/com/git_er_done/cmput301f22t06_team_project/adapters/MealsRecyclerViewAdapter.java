package com.git_er_done.cmput301f22t06_team_project.adapters;

import static com.git_er_done.cmput301f22t06_team_project.MainActivity.dummyMeals;
import static com.git_er_done.cmput301f22t06_team_project.fragments.MealPlannerFragment.getSelectedDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.MealDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO - add a prompt that tells the user if they don't have any meals planned on the selected date
public class MealsRecyclerViewAdapter extends RecyclerView.Adapter<MealsRecyclerViewAdapter.ViewHolder>{

//    private final IngredientsRecyclerViewInterface rvInterface;
    private List<Meal> mMeals = new ArrayList<>();

    View mealView;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        mealView = inflater.inflate(R.layout.meal_list_item, parent, false);

        // Return a new holder instance
        return new MealsRecyclerViewAdapter.ViewHolder(mealView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the meal at this position in the list
        Meal meal = mMeals.get(position);
        holder.mealIngredients = meal.getOnlyIngredientsFromMeal();
        holder.mealRecipes = meal.getOnlyRecipesFromMeal();

//        holder.mealIngredientListViewAdapter.clear();
//        holder.mealIngredients = meal.getOnlyIngredientsFromMeal();
//
//        holder.mealRecipeListViewAdapter.clear();
//        holder.mealRecipes = meal.getOnlyRecipesFromMeal();

//        //Loop  through all the ingredients in a meal and add them to the adapter for the ingredient listview for this particular meal
//        for(int i = 0; i < holder.mealRecipes.size(); i++){
//            holder.mealRecipeListViewAdapter.add(holder.mealRecipes.get(i));
//            holder.mealRecipeListViewAdapter.notifyDataSetChanged();
//        }
//        holder.mealRecipeListViewAdapter.setListViewHeightBasedOnChildren(holder.recipesListView);

//        //Loop  through all the ingredients in a meal and add them to the adapter for the ingredient listview for this particular meal
//        for(int i = 0; i < holder.mealIngredients.size(); i++){
//            holder.mealIngredientListViewAdapter.add(holder.mealIngredients.get(i));
//            holder.mealIngredientListViewAdapter.notifyDataSetChanged();
//        }
//        holder.mealIngredientListViewAdapter.setListViewHeightBasedOnChildren(holder.ingredientsListView);

        ((LinearLayout)holder.linearRecipes).removeAllViews(); //remove
        for(int i = 0; i < holder.mealRecipes.size(); i++){
            TextView tvRecipeName = new TextView(holder.itemView.getContext());
            tvRecipeName.setText(holder.mealRecipes.get(i).getTitle());
            tvRecipeName.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout)holder.linearRecipes).addView(tvRecipeName);
        }

        ((LinearLayout)holder.linearIngredients).removeAllViews();
        for(int i = 0; i < holder.mealIngredients.size(); i++){
            TextView textView = new TextView(holder.itemView.getContext());
            textView.setText(holder.mealIngredients.get(i).getName());
            textView.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout)holder.linearIngredients).addView(textView);
        }

    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }

    /**
     * Gets the new selected date - fetches the meals associated with this date from the DB and populates the recyclerview
     * This is called each time the user selects a new date (current date changes)
     */
    public void updateRVToSelectedDate(LocalDate newlySelectedDate){
        mMeals.clear();

        mMeals.addAll(MealDBHelper.getMealsFromStorageAtDate(newlySelectedDate));

//        //Loop through all dummy meals - only add ones with selected date as date to the adapter
//        for(int i = 0; i < dummyMeals.size(); i++){
//            if(dummyMeals.get(i).getDate().equals(newlySelectedDate)){
//                mMeals.add(dummyMeals.get(i));
//            }
//        }
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        mMeals.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Meal newMeal){
        mMeals.add(newMeal);
        notifyDataSetChanged();
    }

    public void modifyMealInAdapter(Meal meal, int position){
        mMeals.set(position, meal);
        notifyDataSetChanged();
    }

    // Direct reference to each of the views within a data item. Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ListView ingredientsListView;
        ListView recipesListView;

        View linearIngredients;
        View linearRecipes;

        ArrayList<Recipe> mealRecipes = new ArrayList<>();
        MealRecipeListViewAdapter mealRecipeListViewAdapter = new MealRecipeListViewAdapter(mealView.getContext(), mealRecipes);

        ArrayList<Ingredient> mealIngredients = new ArrayList<>();
        MealIngredientListViewAdapter mealIngredientListViewAdapter = new MealIngredientListViewAdapter(mealView.getContext(), mealIngredients);


        //Constructor accepts entire item row and does view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores itemView in a public final member variable that can be used to access context from any ViewHolder instance
            super(itemView);

            linearIngredients = itemView.findViewById(R.id.ll_ingredients);

            linearRecipes = itemView.findViewById(R.id.ll_recipes);

            recipesListView = itemView.findViewById(R.id.lv_recipes_in_meal);
            recipesListView.setScrollContainer(false);
            recipesListView.setAdapter(mealRecipeListViewAdapter);

            ingredientsListView = itemView.findViewById(R.id.lv_ingredients_in_meal);
            ingredientsListView.setScrollContainer(false);
            ingredientsListView.setAdapter(mealIngredientListViewAdapter);

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    if(rvInterface != null){
//                        int pos = getAdapterPosition();
//                        //ensure pos long clicked is valid
//                        if(pos != RecyclerView.NO_POSITION){
//                            rvInterface.onItemLongClick(pos);
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//            });
        }
    }
}
