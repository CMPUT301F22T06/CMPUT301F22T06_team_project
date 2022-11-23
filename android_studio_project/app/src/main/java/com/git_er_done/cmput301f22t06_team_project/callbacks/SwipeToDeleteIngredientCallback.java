package com.git_er_done.cmput301f22t06_team_project.callbacks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.adapters.IngredientsRecyclerViewAdapter;

public class SwipeToDeleteIngredientCallback extends ItemTouchHelper.Callback {

    private IngredientsRecyclerViewAdapter mAdapter;

    public SwipeToDeleteIngredientCallback(IngredientsRecyclerViewAdapter adapter){mAdapter = adapter;}

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
        mAdapter.fakeDeleteForUndo(position);
    }

}
