package com.git_er_done.cmput301f22t06_team_project.fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import java.util.Collections;
import java.util.Comparator;
import androidx.core.view.MenuHost;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.RecipesRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.SwipeToDeleteRecipeCallback;
import com.git_er_done.cmput301f22t06_team_project.controllers.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipesDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment implements RecipesRecyclerViewInterface, MenuProvider
{
    RecyclerView rvRecipes;
    RecipesRecyclerViewAdapter rvAdapter;
    FloatingActionButton fabAddRecipe;
    static ProgressBar progressBar;

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recipes");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ingredients List");

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.recipe_sort_menu, menu);
                // Add option Menu Here

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.action_sort_by_title:
                        rvAdapter.sortByTitle();
                        break;

                    case R.id.action_sort_by_preptime:
                        rvAdapter.sortByPrepTime();
                        break;

                    case R.id.action_sort_by_servings:
                        rvAdapter.sortByServings();
                        break;

                    case R.id.action_sort_by_recipe_category:
                        rvAdapter.sortByCategory();
                        break;
                }


                return false;
                // Handle option Menu Here

            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recipes");

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_recipes, container, false);
        rvRecipes = (RecyclerView) root.findViewById(R.id.rv_recipes_list);
//        rvRecipes.setHasFixedSize(true);
//        rvRecipes.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        testRecipes = new ArrayList<>();
//        rvAdapter = new RecipesRecyclerViewAdapter(testRecipes, this);
//        rvRecipes.setAdapter(rvAdapter);
        fabAddRecipe = root.findViewById(R.id.fab_recipe_add);
        progressBar = root.findViewById(R.id.progressBarId);

        setupRecyclerView();

        fabAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        RecipesDBHelper dbHelper = new RecipesDBHelper(rvAdapter);
        //dbHelper.setRecipesAdapter(rvAdapter, testRecipes);

        Drawable d = ContextCompat.getDrawable(getContext(), R.drawable.splash_image);
        root.setBackground(d);

        // Inflate the layout for this fragment
        return root;
    }

    private void setupRecyclerView(){
        rvAdapter = new RecipesRecyclerViewAdapter(this);
        rvRecipes.setAdapter(rvAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteRecipeCallback(rvAdapter));
        itemTouchHelper.attachToRecyclerView(rvRecipes);
    }

    private void showEditDialog(Recipe selectedRecipe) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        RecipeAddEditDialogFragment editNameDialogFragment =
                RecipeAddEditDialogFragment.newInstance(
                        selectedRecipe, rvAdapter);
        editNameDialogFragment.show(fm, "fragment_recipe_add_edit_dialog");

    }

    private void showAddDialog() {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        RecipeAddEditDialogFragment editNameDialogFragment =
                RecipeAddEditDialogFragment.newInstance(
                        rvAdapter);
        editNameDialogFragment.show(fm, "fragment_recipe_add_edit_dialog");
    }

    @Override
    public void onItemLongClick(int position) {
        Recipe selectedRecipe = rvAdapter.getItem(position);
        //Create a dialog displaying all of the selected Recipes attributes
        showEditDialog(selectedRecipe);

    }
    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.recipe_sort_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
    static public void onDataChange() {
        progressBar.setVisibility(View.GONE);
    }

}