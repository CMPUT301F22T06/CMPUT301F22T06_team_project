package com.git_er_done.cmput301f22t06_team_project.adapters;

import static com.git_er_done.cmput301f22t06_team_project.fragments.MealAddEditDialogFragment.selectedRecipesToAddToMeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.customViews.RecipeMealItemView;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import java.util.ArrayList;

public class MealsSelectedNewRecipesListViewAdapter extends ArrayAdapter<Recipe> {

    //List all available ingredients from storage
    private ArrayList<Recipe> ingredientArrayList;
    private LayoutInflater inflater;

    RecipeMealItemView recipeMealItemView;


    //When the adapter is constructed call the database to get the list of available ingredients
    public MealsSelectedNewRecipesListViewAdapter(@NonNull Context context, ArrayList<Recipe> arrayList) {
        super(context, 0, arrayList);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        Recipe recipe = this.getItem(position);

        RecipeMealItemView recipeMealItemView;

        //Inflate custom layout for this item
        if(currentItemView == null) {
            currentItemView = inflater.inflate(R.layout.meal_recipe_list_item, parent, false);
        }

        recipeMealItemView = new RecipeMealItemView(currentItemView.getContext());
        recipeMealItemView.setTitle(recipe.getTitle());
        recipeMealItemView.setServings(recipe.getServings());

        recipeMealItemView.buttonAddMealRecipeServing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexOfCurrentRecipe = selectedRecipesToAddToMeal.indexOf(recipe);
                if(recipe.getServings() < 999) {
                    selectedRecipesToAddToMeal.get(indexOfCurrentRecipe).setServings(recipe.getServings() + 1);
                    recipeMealItemView.setServings(selectedRecipesToAddToMeal.get(indexOfCurrentRecipe).getServings());
                }
            }
        });

        recipeMealItemView.buttonMinusMealRecipeServing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexOfCurrentRecipe = selectedRecipesToAddToMeal.indexOf(recipe);
                if(recipe.getServings() > 1) {
                    selectedRecipesToAddToMeal.get(indexOfCurrentRecipe).setServings(recipe.getServings() - 1);
                    recipeMealItemView.setServings(selectedRecipesToAddToMeal.get(indexOfCurrentRecipe).getServings());
                }
            }
        });

        return recipeMealItemView;
    }
}
