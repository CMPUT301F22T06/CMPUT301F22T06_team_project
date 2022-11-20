package com.git_er_done.cmput301f22t06_team_project.controllers;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientsViewAdapter extends ArrayAdapter<RecipeIngredient> {
    private ArrayList<RecipeIngredient> recipeIngredients;
    private Context context;


    public RecipeIngredientsViewAdapter(ArrayList<RecipeIngredient> recipeIngredients, Context context){
        super(context, R.layout.recipe_ingredient_item_layout);
        this.context = context;
        this.recipeIngredients = recipeIngredients;
    }


}
