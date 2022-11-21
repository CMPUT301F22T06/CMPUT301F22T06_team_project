package com.git_er_done.cmput301f22t06_team_project.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.RecipesRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.SwipeToDeleteRecipeCallback;
import com.git_er_done.cmput301f22t06_team_project.controllers.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipesDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment implements RecipesRecyclerViewInterface
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    @Override
    public void onItemDeleted(Recipe recipe, int position) {
        Snackbar snackbar = Snackbar.make(this.getView(), "Are You sure you want to delete this?",
                Snackbar.LENGTH_LONG);
        snackbar.setAction("DELETE", v -> RecipesDBHelper.deleteRecipe(recipe, position));
        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                    // IF Snackbar closed on its own, item was NOT deleted
                    rvAdapter.notifyDataSetChanged();
                }
            }
        });
        snackbar.show();
    }

}