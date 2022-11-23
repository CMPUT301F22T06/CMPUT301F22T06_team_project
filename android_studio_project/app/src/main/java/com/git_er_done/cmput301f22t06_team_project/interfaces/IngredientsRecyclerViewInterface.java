package com.git_er_done.cmput301f22t06_team_project.interfaces;


import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

/**
 * Interface for handling IngredientRecyclerView user interaction for add/edit and delete ingredient
 */
public interface IngredientsRecyclerViewInterface {
    void onItemLongClick(int position);
    void onItemDeleted(Ingredient ing, int position);
}
