package com.git_er_done.cmput301f22t06_team_project.adapters;

import static com.git_er_done.cmput301f22t06_team_project.MainActivity.dummyMeals;
import static com.git_er_done.cmput301f22t06_team_project.fragments.MealPlannerFragment.getSelectedDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.interfaces.IngredientsRecyclerViewInterface;
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
    public void onBindViewHolder(@NonNull MealsRecyclerViewAdapter.ViewHolder holder, int position) {
        //Get the meal at this position in the list
        Meal meal = mMeals.get(position);
        ArrayList<Recipe> recipes = meal.getRecipesFromMeal();
        ArrayList<Ingredient> ingredients = meal.getIngredientsFromMeal();
        LocalDate selectedDate = getSelectedDate();

        TextView test = holder.testText;

        test.setText(meal.getId().toString());


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
        //Loop through all dummy meals - only add ones with selected date as date to the adapter
        for(int i = 0; i < dummyMeals.size(); i++){
            if(dummyMeals.get(i).getDate().equals(newlySelectedDate)){
                mMeals.add(dummyMeals.get(i));
            }
        }

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
        public TextView testText;


        //Constructor accepts entire item row and does view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores itemView in a public final member variable that can be used to access context from any ViewHolder instance
            super(itemView);

            testText = itemView.findViewById(R.id.tv_meal_text_test);

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
