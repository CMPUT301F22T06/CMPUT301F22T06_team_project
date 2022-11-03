package com.git_er_done.cmput301f22t06_team_project.fragments;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.git_er_done.cmput301f22t06_team_project.IngredientsRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.controllers.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.FirebaseCallback;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeTypes.BreakFastRecipe;

import java.util.ArrayList;

import io.grpc.ManagedChannelProvider;

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
        testIngredients = new ArrayList<>();
        rvAdapter = new IngredientsRecyclerViewAdapter(testIngredients, this);
        rvIngredients.setAdapter(rvAdapter);

        IngredientDBHelper dbHelper = new IngredientDBHelper();
        dbHelper.setIngredientsAdapter(rvAdapter, testIngredients);

        // Inflate the layout for this fragment
        return root;
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
//        Ingredient selectedIngredient = rvAdapter.getItem(position);
//        //Create a dialog displaying all of the selected ingredients attributes
//        showEditDialog(selectedIngredient);
        Ingredient selectedIngredient = rvAdapter.getItem(position);
        //Create a dialog displaying all of the selected ingredients attributes
        Recipe fruit_salad = new BreakFastRecipe("perfect summer fruit salad", "mybaa82\n" +
                "It was great. I may change it up next time but for now, perfect\n" +
                "\n" +
                "Barb Gregory\n" +
                "I did not make any changes. Made it exactly as the recipe called for. It was easy to make and everyone loved the taste. I will make it again\n" +
                "\n" +
                "Morgon Barg\n" +
                "I love this recipe! The sauce is amazing. I have been making it for the 4th of July and it has become a repeat request dish for me to bring! Thank you!!", "breakfast", 30, 10);

        ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
        RecipeIngredient appleRecipe = new RecipeIngredient("apple","g",2, "slice into eighths");
        RecipeIngredient orangeRecipe = new RecipeIngredient("orange","g", 2, "take apart at its seams");
        recipeIngredients.add(appleRecipe);
        recipeIngredients.add(orangeRecipe);
        showEditDialog(fruit_salad);

    }
}