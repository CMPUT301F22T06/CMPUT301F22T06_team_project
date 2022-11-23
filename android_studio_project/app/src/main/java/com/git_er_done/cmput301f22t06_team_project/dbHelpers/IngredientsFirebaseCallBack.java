package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

public interface IngredientsFirebaseCallBack {
    void onCallback(Ingredient retrievedIngredients);
}
