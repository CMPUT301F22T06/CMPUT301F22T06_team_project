package com.git_er_done.cmput301f22t06_team_project;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.controllers.RecipesRecyclerViewAdapter;

public class SwipeToDeleteRecipeCallback extends ItemTouchHelper.Callback {

    private RecipesRecyclerViewAdapter mAdapter;

    public SwipeToDeleteRecipeCallback(RecipesRecyclerViewAdapter adapter){
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
        //mAdapter.removeRecipe(position);
        mAdapter.fakeDeleteForUndo(position);
    }

}
