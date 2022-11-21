package com.git_er_done.cmput301f22t06_team_project.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
                        Collections.sort(testRecipes, new Comparator<Recipe>(){
                            @Override
                            public int compare(Recipe lhs, Recipe rhs) {
                                return lhs.getTitle().compareTo(rhs.getTitle());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_sort_by_preptime:
                        Collections.sort(testRecipes, new Comparator<Recipe>(){
                            @Override
                            public int compare(Recipe lhs, Recipe rhs) {
                                return lhs.getPrep_time().compareTo(rhs.getPrep_time());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_sort_by_servings:
                        Collections.sort(testRecipes, new Comparator<Recipe>(){
                            @Override
                            public int compare(Recipe lhs, Recipe rhs) {
                                return lhs.getServings().compareTo(rhs.getServings());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_sort_by_recipe_category:
                        Collections.sort(testRecipes, new Comparator<Recipe>(){
                            @Override
                            public int compare(Recipe lhs, Recipe rhs) {
                                return lhs.getCategory().compareTo(rhs.getCategory());
                            }
                        });
                        rvAdapter.notifyDataSetChanged();

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

        setupRecyclerView();

        fabAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        RecipesDBHelper dbHelper = new RecipesDBHelper(rvAdapter);
        //dbHelper.setRecipesAdapter(rvAdapter, testRecipes);

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

}