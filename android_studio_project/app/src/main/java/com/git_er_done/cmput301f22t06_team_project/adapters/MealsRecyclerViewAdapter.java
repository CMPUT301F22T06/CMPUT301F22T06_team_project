package com.git_er_done.cmput301f22t06_team_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.MealDBHelper;
import com.git_er_done.cmput301f22t06_team_project.customViews.IngredientMealItemView;
import com.git_er_done.cmput301f22t06_team_project.customViews.RecipeMealItemView;
import com.git_er_done.cmput301f22t06_team_project.fragments.MealPlannerFragment;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO - add a prompt that tells the user if they don't have any meals planned on the selected date
//TODO - if a user sets an ingredient amount or recipe serving to zero, prompt to ask if they are sure and that it will be removed from the meal if so
//TODO - add swipe to delete an ingredient or recipe from a meal
public class MealsRecyclerViewAdapter extends RecyclerView.Adapter<MealsRecyclerViewAdapter.ViewHolder>{

    private List<Meal> mealRecyclerViewList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View mealView = inflater.inflate(R.layout.meal_list_item, parent, false);

        // Return a new holder instance
        return new MealsRecyclerViewAdapter.ViewHolder(mealView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = mealRecyclerViewList.get(position); //Get the meal at this position in the list
        holder.mealIngredients = meal.getOnlyIngredientsFromMeal();
        holder.mealRecipes = meal.getOnlyRecipesFromMeal();

        ((LinearLayout) holder.recipesLinearLayout).removeAllViews(); //Remove views previously bound for a different date
        if (holder.mealRecipes.size() > 0) {
            for (int i = 0; i < holder.mealRecipes.size(); i++) {
                Recipe currentRecipe = holder.mealRecipes.get(i);
                holder.recipeMealItemView = new RecipeMealItemView(holder.itemView.getContext());

                holder.recipeMealItemView.setTitle(holder.mealRecipes.get(i).getTitle());
                holder.recipeMealItemView.setServings(holder.mealRecipes.get(i).getServings());

                holder.recipeMealItemView.buttonAddMealRecipeServing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentRecipe.getServings() < 999) {
                            //update the serving size of the particular recipe modified
                            ArrayList<Recipe> modifedRecipes = meal.getRecipesFromMeal();
                            int recipeIndex = modifedRecipes.indexOf(currentRecipe);
                            modifedRecipes.get(recipeIndex).setServings(currentRecipe.getServings() + 1);

                            Meal newMeal = new Meal(meal.getId(), modifedRecipes, meal.getOnlyIngredientsFromMeal(), MealPlannerFragment.getSelectedDate());

                            MealDBHelper.modifyMealInDB(newMeal, meal, holder.getAdapterPosition());
                        }
                    }
                });

                holder.recipeMealItemView.buttonMinusMealRecipeServing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentRecipe.getServings() > 0) {
                            //update the serving size of the particular recipe modified
                            ArrayList<Recipe> modifedRecipes = meal.getRecipesFromMeal();
                            int recipeIndex = modifedRecipes.indexOf(currentRecipe);
                            modifedRecipes.get(recipeIndex).setServings(currentRecipe.getServings() - 1);

                            Meal newMeal = new Meal(meal.getId(), modifedRecipes, meal.getOnlyIngredientsFromMeal(), MealPlannerFragment.getSelectedDate());

                            MealDBHelper.modifyMealInDB(newMeal, meal, holder.getAdapterPosition());
                        }
                    }
                });

                ((LinearLayout) holder.recipesLinearLayout).addView(holder.recipeMealItemView);
            }
        }
        else{
            //If meal contains no recipes, make the recipes title invisible
        }

        ((LinearLayout)holder.ingredientsLinearLayout).removeAllViews(); //Remove views previously bound for a different date
        for(int i = 0; i < holder.mealIngredients.size(); i++){
            holder.ingredientMealItemView = new IngredientMealItemView(holder.itemView.getContext());

            holder.ingredientMealItemView.setName(holder.mealIngredients.get(i).getName());
            holder.ingredientMealItemView.setAmount(holder.mealIngredients.get(i).getAmount());
            holder.ingredientMealItemView.setUnit(holder.mealIngredients.get(i).getUnit());
            ((LinearLayout) holder.ingredientsLinearLayout).addView(holder.ingredientMealItemView);
        }

        holder.deleteMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealDBHelper.deleteMealFromDB(meal.getId().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealRecyclerViewList.size();
    }

    /**
     * Gets the new selected date - fetches the meals associated with this date from the DB and populates the recyclerview
     * This is called each time the user selects a new date (current date changes)
     */
    public void updateRVToSelectedDate(LocalDate newlySelectedDate){
        mealRecyclerViewList.clear();

        for(int i = 0; i < MealDBHelper.getMealsFromStorageAtDate(newlySelectedDate).size(); i++){
            mealRecyclerViewList.add(MealDBHelper.getMealsFromStorageAtDate(newlySelectedDate).get(i));
        }

        //Loop through all dummy meals - only add ones with selected date as date to the adapter
//        for(int i = 0; i < dummyMeals.size(); i++){
//            if(dummyMeals.get(i).getDate().equals(newlySelectedDate)){
//                mealRecyclerViewList.add(dummyMeals.get(i));
//            }
//        }
        notifyDataSetChanged();
    }

    public void removeMealFromRecyclerViewList(String uuidString){
        mealRecyclerViewList.remove(getAdapterPositionOfMealFromUUID(uuidString));
        notifyDataSetChanged();
    }

    public void addMealToRecyclerViewList(Meal newMeal){
        mealRecyclerViewList.add(newMeal);
        notifyDataSetChanged();
    }

    public void modifyMealInRecyclerViewList(Meal meal, int position){
        mealRecyclerViewList.set(position, meal);
        notifyDataSetChanged();
    }

    public ArrayList<Meal> getMealsList(){
        return (ArrayList<Meal>) mealRecyclerViewList;
    }

    public Meal getMealFromUUID(String mealUUIDString){
        for(int i = 0; i < mealRecyclerViewList.size(); i++){
            if((mealRecyclerViewList.get(i).getId().toString()).equals(mealUUIDString)){
                return  mealRecyclerViewList.get(i);
            }
        }
        return null;
    }

    public int getAdapterPositionOfMealFromUUID(String mealUUIDString){
        int res = -1;
        for(int i = 0; i < mealRecyclerViewList.size(); i++){
            if((mealRecyclerViewList.get(i).getId().toString()).equals(mealUUIDString)){
                return  i;
            }
        }
        return res;
    }

    // Direct reference to each of the views within a data item. Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        View ingredientsLinearLayout;
        View recipesLinearLayout;
        Button deleteMealButton;

        IngredientMealItemView ingredientMealItemView;
        RecipeMealItemView recipeMealItemView;

        ArrayList<Recipe> mealRecipes = new ArrayList<>();
        ArrayList<Ingredient> mealIngredients = new ArrayList<>();

        //Constructor accepts entire item row and does view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores itemView in a public final member variable that can be used to access context from any ViewHolder instance
            super(itemView);

            ingredientsLinearLayout = itemView.findViewById(R.id.ll_ingredients);
            recipesLinearLayout = itemView.findViewById(R.id.ll_recipes);
            deleteMealButton = itemView.findViewById(R.id.btn_meal_delete_meal);
        }
    }
}
