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
import com.git_er_done.cmput301f22t06_team_project.customViews.IngredientMealItemView;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

import java.util.ArrayList;


/**
 * Shows a listview of all available ingredients in storage. Just shows the name of the ingredient.
 * User selects the ingredients they want to add, then dialog closes and returns these to the meal add dialog.
 * The meal add dialog then populates its linear layout of ingredients with the selected all set to amount or serving size of 1
 *
 */
public class MealsAddIngredientListViewAdapter extends ArrayAdapter<Ingredient> {

    //List all available ingredients from storage
    private ArrayList<Ingredient> ingredientArrayList;
    private LayoutInflater inflater;


    //When the adapter is constructed call the database to get the list of available ingredients
    public MealsAddIngredientListViewAdapter(@NonNull Context context, ArrayList<Ingredient> arrayList) {
        super(context, 0, arrayList);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        Ingredient ingredient = this.getItem(position);

        CheckBox ingredientCheckBox;
        TextView ingredientName;

        //Inflate custom layout for this item
        if(currentItemView == null) {
            currentItemView = inflater.inflate(R.layout.meal_add_ingredient_or_recipe_list_item, parent, false);
        }

        ingredientCheckBox = currentItemView.findViewById(R.id.cb_meal_add_ingredient_or_recipe);
        ingredientName = currentItemView.findViewById(R.id.tv_meal_add_ingredient_or_recipe_list_item_name);

        ingredientName.setText(ingredient.getName());

        ingredientCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredient.setChecked(ingredientCheckBox.isChecked());
            }
        });

        return currentItemView;
    }
}
