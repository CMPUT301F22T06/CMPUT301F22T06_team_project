package com.git_er_done.cmput301f22t06_team_project.fragments;

import static android.content.ContentValues.TAG;

import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.testIngredients;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.git_er_done.cmput301f22t06_team_project.IngredientsRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.SwipeToDeleteCallback;
import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Location;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.FirebaseCallback;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import io.grpc.ManagedChannelProvider;

public class IngredientsFragment extends Fragment implements IngredientsRecyclerViewInterface {

    RecyclerView rvIngredients;
    FloatingActionButton fabAddIngredient;
    IngredientsRecyclerViewAdapter rvAdapter;


    /**
     * Required empty constructor
     */
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
        fabAddIngredient = root.findViewById(R.id.fab_ingredient_add);

        setupRecyclerView();

        fabAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        IngredientDBHelper dbHelper = new IngredientDBHelper(rvAdapter);
//        dbHelper.setIngredientsAdapter(rvAdapter, testIngredients);

        // Inflate the layout for this fragment
        return root;
    }

    private void setupRecyclerView(){
        rvAdapter = new IngredientsRecyclerViewAdapter(testIngredients, this);
        rvIngredients.setAdapter(rvAdapter);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(rvAdapter));
        itemTouchHelper.attachToRecyclerView(rvIngredients);
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
        showEditDialog(selectedIngredient);
    }
}