package com.git_er_done.cmput301f22t06_team_project;

import static com.git_er_done.cmput301f22t06_team_project.MainActivity.ingredientRVAdapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.controllers.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.fragments.IngredientsFragment;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;
import com.google.android.material.snackbar.Snackbar;

public class SwipeToDeleteCallback extends ItemTouchHelper.Callback {

    private IngredientsRecyclerViewAdapter mAdapter;

    public SwipeToDeleteCallback(IngredientsRecyclerViewAdapter adapter){
        mAdapter = adapter;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        ingredientRVAdapter.fakeDeleteForUndo(position);
    }

}
