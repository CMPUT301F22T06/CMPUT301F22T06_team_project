package com.git_er_done.cmput301f22t06_team_project.interfaces;

//Respond to user clicks on items in recipe reyclerView

import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;

/**
 * Interface for handling RecipesRecyclerView user interaction for add/edit and delete recipe
 */
public interface RecipesRecyclerViewInterface {
    void onItemLongClick(int position);
    void onItemDeleted(Recipe recipe, int position);

}
