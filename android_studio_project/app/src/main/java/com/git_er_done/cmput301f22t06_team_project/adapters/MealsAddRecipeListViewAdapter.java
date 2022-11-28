package com.git_er_done.cmput301f22t06_team_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipeDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

import java.util.ArrayList;


/**
 * Shows a listview of all available recipes in storage. Just shows the name of the recipe.
 * User selects the recipes they want to add, then dialog closes and returns these to the meal add dialog.
 * The meal add dialog then populates its linear layout of recipes with the selected all set to amount or serving size of 1
 *
 */
public class MealsAddRecipeListViewAdapter extends ArrayAdapter<Recipe> {

    //List all available recipes from storage
    private ArrayList<Recipe> recipeArrayList;
    private LayoutInflater inflater;


    //When the adapter is constructed call the database to get the list of available recipes
    public MealsAddRecipeListViewAdapter(@NonNull Context context, ArrayList<Recipe> arrayList) {
        super(context, 0, arrayList);
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        Recipe recipe = this.getItem(position);

        CheckBox recipeCheckBox;
        TextView recipeName;

        //Inflate custom layout for this item
        if(currentItemView == null) {
            currentItemView = inflater.inflate(R.layout.meal_add_ingredient_or_recipe_list_item, parent, false);
        }

        recipeCheckBox = currentItemView.findViewById(R.id.cb_meal_add_ingredient_or_recipe);
        recipeName = currentItemView.findViewById(R.id.tv_meal_add_ingredient_or_recipe_list_item_name);
        recipeName.setText(recipe.getTitle());

        recipeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe.setChecked(recipeCheckBox.isChecked());
            }
        });

        return currentItemView;
    }
}
