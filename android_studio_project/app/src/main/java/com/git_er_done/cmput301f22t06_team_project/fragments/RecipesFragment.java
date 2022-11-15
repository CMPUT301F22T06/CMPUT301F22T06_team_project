package com.git_er_done.cmput301f22t06_team_project.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git_er_done.cmput301f22t06_team_project.IngredientsRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.RecipesRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.controllers.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipesDBHelper;

import com.git_er_done.cmput301f22t06_team_project.models.Recipe;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment implements RecipesRecyclerViewInterface
{
    ArrayList<Recipe> testRecipes;
    RecyclerView rvRecipes;
    RecipesRecyclerViewAdapter rvAdapter;

    ArrayList<Recipe> retrievedRecipes;
    public RecipesFragment() {
        // Required empty public constructor
    }




    private void showEditDialog(Recipe selectedRecipe) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        RecipeAddEditDialogFragment editNameDialogFragment =
                RecipeAddEditDialogFragment.newInstance(
                        "Edit Recipe Dialog",
                        selectedRecipe);
        editNameDialogFragment.show(fm, "fragment_recipe_add_edit_dialog");

    }

    @Override
    public void onItemLongClick(int position) {
        Recipe selectedRecipe = rvAdapter.getItem(position);
        //Create a dialog displaying all of the selected Recipes attributes
        showEditDialog(selectedRecipe);

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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_recipes, container, false);
        rvRecipes = (RecyclerView) root.findViewById(R.id.rv_recipes_list);
        rvRecipes.setHasFixedSize(true);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this.getContext()));
        testRecipes = new ArrayList<>();
        rvAdapter = new RecipesRecyclerViewAdapter(testRecipes, this);
        rvRecipes.setAdapter(rvAdapter);

        RecipesDBHelper dbHelper = new RecipesDBHelper();
        dbHelper.setRecipesAdapter(rvAdapter, testRecipes);

        // Inflate the layout for this fragment
        return root;
//        testRecipes = Recipe.createRecipeList();
//        RecipeDBHelper dbHelper = new RecipeDBHelper();
//        for (Recipe recipe: testRecipes) {
//            dbHelper.addRecipe(recipe);
//        }
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_recipes, container, false);
    }
}