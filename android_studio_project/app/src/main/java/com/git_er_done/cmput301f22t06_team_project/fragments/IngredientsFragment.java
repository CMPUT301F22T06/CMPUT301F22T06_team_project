package com.git_er_done.cmput301f22t06_team_project.fragments;


import static com.git_er_done.cmput301f22t06_team_project.MainActivity.ingredientRVAdapter;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.git_er_done.cmput301f22t06_team_project.IngredientsRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.SwipeToDeleteCallback;
import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.google.android.material.snackbar.Snackbar;

//TODO - ON START, CHECK IF ANY INGREDIENTS ARE PAST DUE DATE. IF THEY ARE , SET COUNT TO ZERO AND HIGHLIGH
//  ITEM IN INGREDIENT LIST RED WITH TOAST MESSAGE.

public class IngredientsFragment extends Fragment implements IngredientsRecyclerViewInterface, MenuProvider{

    RecyclerView rvIngredients;
    FloatingActionButton fabAddIngredient;
    private IngredientsRecyclerViewAdapter rvAdapter;


    /**
     * Required empty constructor
     */
    public IngredientsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            rvAdapter = new IngredientsRecyclerViewAdapter();
            IngredientDBHelper ingredientDBHelper = new IngredientDBHelper(rvAdapter);
        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ingredients List");
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ingredient, container, false);

        rvIngredients = (RecyclerView) root.findViewById(R.id.rv_ingredients_list);
        fabAddIngredient = root.findViewById(R.id.fab_ingredient_add);

        setupRecyclerView();

        setupFab();

        setupSortMenu();

        //TODO - Fix this . No point in creating an instance of the DBhelper when its methods are all static
        //Creates an instance of DB helper to initiate event listener and pass reference of RV adapter
//        IngredientDBHelper dbHelper = new IngredientDBHelper(ingredientRVAdapter);

        // Inflate the layout for this fragment
        return root;
    }

    void setupFab(){
        fabAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }

    void setupSortMenu(){
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.ingredient_sort_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id){

                    case R.id.action_sort_by_name:
                        ingredientRVAdapter.sortByName();
                        break;

                    case R.id.action_sort_by_description:
                        ingredientRVAdapter.sortByDescription();
                        break;

                    case R.id.action_sort_by_best_before_date:
                        ingredientRVAdapter.sortByBestBeforeDate();
                        break;

                    case R.id.action_sort_by_location:
                        ingredientRVAdapter.sortByLocation();
                        break;

                    case R.id.action_sort_by_amount:
                        ingredientRVAdapter.sortByAmount();
                        break;

                    case R.id.action_sort_by_unit:
                        ingredientRVAdapter.sortByUnit();
                        break;

                    case R.id.action_sort_by_category:
                        ingredientRVAdapter.sortByCategory();
                        break;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void setupRecyclerView(){
//        ingredientRVAdapter = new IngredientsRecyclerViewAdapter(this);
        rvIngredients.setAdapter(ingredientRVAdapter);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(ingredientRVAdapter));
        itemTouchHelper.attachToRecyclerView(rvIngredients);
    }

    private void showEditDialog(Ingredient selectedIngredient) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        IngredientAddEditDialogFragment editNameDialogFragment =
                IngredientAddEditDialogFragment.newInstance(
                        selectedIngredient, ingredientRVAdapter);
        editNameDialogFragment.show(fm, "fragment_ingredient_add_edit_dialog");
    }

    private void showAddDialog() {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        IngredientAddEditDialogFragment editNameDialogFragment =
                IngredientAddEditDialogFragment.newInstance(
                        ingredientRVAdapter);
        editNameDialogFragment.show(fm, "fragment_ingredient_add_edit_dialog");
    }

    @Override
    public void onItemLongClick(int position) {
        Ingredient selectedIngredient = ingredientRVAdapter.getItem(position);
        showEditDialog(selectedIngredient);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.ingredient_sort_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onItemDeleted(Ingredient ing, int position) {
            Snackbar snackbar = Snackbar.make(this.getView(), "Are You sure you want to delete this?",
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("DELETE", v -> IngredientDBHelper.deleteIngredientFromDB(ing, position));
            snackbar.addCallback(new Snackbar.Callback() {

                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                        // IF Snackbar closed on its own, item was NOT deleted
                        ingredientRVAdapter.notifyDataSetChanged();
                    }
                }
            });
            snackbar.show();
    }


}