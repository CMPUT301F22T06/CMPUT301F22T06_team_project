package com.git_er_done.cmput301f22t06_team_project;


import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;

//Respond to user clicks on items in ingredients reyclerView
public interface IngredientsRecyclerViewInterface {
    void onItemLongClick(int position);
    void onItemDeleted(Ingredient ing, int position);
}
