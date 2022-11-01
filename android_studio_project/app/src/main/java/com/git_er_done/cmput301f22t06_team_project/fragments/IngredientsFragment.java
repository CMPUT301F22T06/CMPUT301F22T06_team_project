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
import android.widget.Toast;

import com.git_er_done.cmput301f22t06_team_project.IngredientsRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment implements IngredientsRecyclerViewInterface {

    ArrayList<Ingredient> testIngredients;
    RecyclerView rvIngredients;
    IngredientsRecyclerViewAdapter rvAdapter;

    ArrayList<Ingredient> retrievedIngredients;
    public IngredientsFragment() {
        // Required empty public constructor
    }


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

        testIngredients = Ingredient.createIngredientList();
        IngredientDBHelper dbHelper = new IngredientDBHelper();
        retrievedIngredients = dbHelper.getAllIngredients();
        rvAdapter = new IngredientsRecyclerViewAdapter(testIngredients, this);
        rvIngredients.setAdapter(rvAdapter);

        // Inflate the layout for this fragment
        return root;
    }

    private void showEditDialog(Ingredient selectedIngredient) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        IngredientAddEditDialogFragment editNameDialogFragment =
                IngredientAddEditDialogFragment.newInstance(
                        "Edit Ingredient Dialog",
                        selectedIngredient);
        editNameDialogFragment.show(fm, "fragment_ingredient_add_edit_dialog");

    }

    @Override
    public void onItemLongClick(int position) {
        Ingredient selectedIngredient = rvAdapter.getItem(position);
        //Create a dialog displaying all of the selected ingredients attributes
        showEditDialog(selectedIngredient);

    }
}