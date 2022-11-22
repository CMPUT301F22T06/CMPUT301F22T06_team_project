package com.git_er_done.cmput301f22t06_team_project.dbHelpers;

import com.git_er_done.cmput301f22t06_team_project.models.Recipe.Recipe;

import java.util.ArrayList;

public interface RecipesFirebaseCallBack {
    void onCallback(ArrayList<Recipe> retrievedRecipes);
}
