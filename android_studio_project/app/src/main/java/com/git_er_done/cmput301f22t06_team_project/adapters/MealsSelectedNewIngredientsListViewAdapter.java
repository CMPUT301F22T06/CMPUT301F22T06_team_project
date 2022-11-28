package com.git_er_done.cmput301f22t06_team_project.adapters;

import static com.git_er_done.cmput301f22t06_team_project.fragments.MealAddEditDialogFragment.selectedIngredientsToAddToMeal;

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

public class MealsSelectedNewIngredientsListViewAdapter extends ArrayAdapter<Ingredient> {

    //List all available ingredients from storage
    private ArrayList<Ingredient> ingredientArrayList;
    private LayoutInflater inflater;


    //When the adapter is constructed call the database to get the list of available ingredients
    public MealsSelectedNewIngredientsListViewAdapter(@NonNull Context context, ArrayList<Ingredient> arrayList) {
        super(context, 0, arrayList);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        Ingredient ingredient = this.getItem(position);

        IngredientMealItemView ingredientMealItemView;

        //Inflate custom layout for this item
        if(currentItemView == null) {
            currentItemView = inflater.inflate(R.layout.meal_ingredient_list_item, parent, false);
        }

        ingredientMealItemView = new IngredientMealItemView(currentItemView.getContext());
        ingredientMealItemView.setName(ingredient.getName());
        ingredientMealItemView.setAmount(ingredient.getAmount());
        ingredientMealItemView.setUnit(ingredient.getUnit());

        ingredientMealItemView.buttonAddMealIngredientAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexOfCurrentIngredient = selectedIngredientsToAddToMeal.indexOf(ingredient);
                if(ingredient.getAmount() < 999) {
                    selectedIngredientsToAddToMeal.get(indexOfCurrentIngredient).setAmount(ingredient.getAmount() + 1);
                    ingredientMealItemView.setAmount(selectedIngredientsToAddToMeal.get(indexOfCurrentIngredient).getAmount());
                }
            }
        });

        ingredientMealItemView.buttonMinusMealIngredientAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexOfCurrentIngredient = selectedIngredientsToAddToMeal.indexOf(ingredient);
                if(ingredient.getAmount() > 1) {
                    selectedIngredientsToAddToMeal.get(indexOfCurrentIngredient).setAmount(ingredient.getAmount() - 1);
                    ingredientMealItemView.setAmount(selectedIngredientsToAddToMeal.get(indexOfCurrentIngredient).getAmount());
                }
            }
        });

        return ingredientMealItemView;
    }
}
