package com.git_er_done.cmput301f22t06_team_project.interfaces;


import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;

//Respond to user clicks on items in ingredients reyclerView
public interface IngredientsRecyclerViewInterface {
    void onItemLongClick(int position);
    void onItemDeleted(Ingredient ing, int position);
}
