package com.git_er_done.cmput301f22t06_team_project.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git_er_done.cmput301f22t06_team_project.IngredientsRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Location;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class IngredientsFragment extends Fragment implements IngredientsRecyclerViewInterface {


    RecyclerView rvIngredients;
    IngredientsRecyclerViewAdapter rvAdapter;

//    ArrayList<Ingredient> retrievedIngredients;

    public IngredientsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ingredient, container, false);
        rvIngredients = (RecyclerView) root.findViewById(R.id.rv_ingredients_list);
        rvIngredients.setHasFixedSize(true);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this.getContext()));

//        IngredientDBHelper dbHelper = new IngredientDBHelper();
//        for (Ingredient ingredient: testIngredients) {
//            dbHelper.addIngredient(ingredient);
//        }
//        dbHelper.deleteIngredient("apple");
//        retrievedIngredients = dbHelper.getAllIngredients();

        rvAdapter = new IngredientsRecyclerViewAdapter(Ingredient.testIngredients, this);
        rvIngredients.setAdapter(rvAdapter);

        FloatingActionButton fabAddIngredient = root.findViewById(R.id.fab_ingredient_add);
        fabAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        return root;
    }

    private void showEditDialog(Ingredient selectedIngredient) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        IngredientAddEditDialogFragment editNameDialogFragment =
                IngredientAddEditDialogFragment.newInstance(
                        selectedIngredient,
                        rvAdapter);
        editNameDialogFragment.show(fm, "fragment_ingredient_add_edit_dialog");
    }

    private void showAddDialog() {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        IngredientAddEditDialogFragment editNameDialogFragment =
                IngredientAddEditDialogFragment.newInstance(
                        rvAdapter);
        editNameDialogFragment.show(fm, "fragment_ingredient_add_edit_dialog");
    }


    @Override
    public void onItemLongClick(int position) {
        Ingredient selectedIngredient = rvAdapter.getItem(position);
        //Create a dialog displaying all of the selected ingredients attributes
        showEditDialog(selectedIngredient);
    }
}