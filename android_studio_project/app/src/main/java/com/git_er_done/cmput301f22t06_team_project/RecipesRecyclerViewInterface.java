package com.git_er_done.cmput301f22t06_team_project;

//Respond to user clicks on items in recipe reyclerView

import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;

public interface RecipesRecyclerViewInterface {
    void onItemLongClick(int position);
    void onItemDeleted(Recipe recipe, int position);

}
