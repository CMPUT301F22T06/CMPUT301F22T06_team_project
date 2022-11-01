package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;

import java.util.ArrayList;

public interface IngredientsFirebaseCallBack {
    void onCallback(ArrayList<Ingredient> retrievedIngredients);
}
