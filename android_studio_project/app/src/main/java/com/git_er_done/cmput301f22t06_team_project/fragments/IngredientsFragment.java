package com.git_er_done.cmput301f22t06_team_project.fragments;

import static android.content.ContentValues.TAG;

import static com.git_er_done.cmput301f22t06_team_project.models.Ingredient.Ingredient.testIngredients;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import io.grpc.ManagedChannelProvider;

//TODO - ON START, CHECK IF ANY INGREDIENTS ARE PAST DUE DATE. IF THEY ARE , SET COUNT TO ZERO AND HIGHLIGH
//  ITEM IN INGREDIENT LIST RED WITH TOAST MESSAGE.

public class IngredientsFragment extends Fragment implements IngredientsRecyclerViewInterface, MenuProvider{

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ingredients List");

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.ingredient_sort_menu, menu);
                // Add option Menu Here

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id){

                    case R.id.action_sort_by_name:
                        Collections.sort(testIngredients, new Comparator<Ingredient>(){
                            @Override
                            public int compare(Ingredient lhs, Ingredient rhs) {
                                return lhs.getName().compareTo(rhs.getName());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_sort_by_description:
                        Collections.sort(testIngredients, new Comparator<Ingredient>(){
                            @Override
                            public int compare(Ingredient lhs, Ingredient rhs) {
                                return lhs.getDesc().compareTo(rhs.getDesc());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_sort_by_best_before_date:
                        Collections.sort(testIngredients, new Comparator<Ingredient>(){
                            @Override
                            public int compare(Ingredient lhs, Ingredient rhs) {
                                return lhs.getBestBefore().compareTo(rhs.getBestBefore());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_sort_by_location:
                        Collections.sort(testIngredients, new Comparator<Ingredient>(){
                            @Override
                            public int compare(Ingredient lhs, Ingredient rhs) {
                                return lhs.getLocation().compareTo(rhs.getLocation());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_sort_by_amount:
                        Collections.sort(testIngredients, new Comparator<Ingredient>(){
                            @Override
                            public int compare(Ingredient lhs, Ingredient rhs) {
                                return lhs.getAmount().compareTo(rhs.getAmount());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_sort_by_unit:
                        Collections.sort(testIngredients, new Comparator<Ingredient>(){
                            @Override
                            public int compare(Ingredient lhs, Ingredient rhs) {
                                return lhs.getUnit().compareTo(rhs.getUnit());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_sort_by_category:
                        Collections.sort(testIngredients, new Comparator<Ingredient>(){
                            @Override
                            public int compare(Ingredient lhs, Ingredient rhs) {
                                return lhs.getCategory().compareTo(rhs.getCategory());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);


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

        //Creates an instance of DB helper to initiate event listener and pass reference of RV adapter
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

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.ingredient_sort_menu, menu);
        ///DO STUFF...
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}