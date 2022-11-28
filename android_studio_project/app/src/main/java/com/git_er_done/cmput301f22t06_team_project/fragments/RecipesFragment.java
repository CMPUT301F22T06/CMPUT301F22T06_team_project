package com.git_er_done.cmput301f22t06_team_project.fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.MealDBHelper;
import com.git_er_done.cmput301f22t06_team_project.interfaces.RecipesRecyclerViewInterface;
import com.git_er_done.cmput301f22t06_team_project.callbacks.SwipeToDeleteRecipeCallback;
import com.git_er_done.cmput301f22t06_team_project.adapters.RecipesRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipeDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.git_er_done.cmput301f22t06_team_project.models.recipe.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment implements RecipesRecyclerViewInterface, MenuProvider {
    RecyclerView rvRecipes;
    RecipesRecyclerViewAdapter rvAdapter;
    FloatingActionButton fabAddRecipe;
    static ProgressBar progressBar;

    /**
     * Required empty public constructor
     */
    public RecipesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ingredients List");

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.recipe_sort_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
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

            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Recipes");

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_recipes, container, false);

        rvRecipes = (RecyclerView) root.findViewById(R.id.rv_recipes_list);
        fabAddRecipe = root.findViewById(R.id.fab_recipe_add);
        progressBar = root.findViewById(R.id.progressBarId);

        setupRecyclerView();

        fabAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        RecipeDBHelper.setupSnapshotListenerForRecipeRVAdapter(rvAdapter);

        Drawable d = ContextCompat.getDrawable(getContext(), R.drawable.splash_image);
        root.setBackground(d);

        // Inflate the layout for this fragment
        return root;
    }

    private void setupRecyclerView() {
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
        // Check if recipe is used in a meal plan
        ArrayList<Meal> meals = MealDBHelper.getMealsFromStorage();
        ArrayList<String> mealRecipes = new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            ArrayList<Recipe> ingredientsArray = meals.get(i).getOnlyRecipesFromMeal();
            for (int j = 0; j < ingredientsArray.size(); j++) {
                mealRecipes.add(ingredientsArray.get(j).getTitle());
            }
        }

        Snackbar snackbar;
        if (mealRecipes.contains(recipe.getTitle())) {
            snackbar = Snackbar.make(this.getView(), "Can't delete this recipe because it is used in a meal plan!",
                    Snackbar.LENGTH_SHORT);
            snackbar.addCallback(new Snackbar.Callback() {

                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                        // IF Snackbar closed on its own, item was NOT deleted
                        rvAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {

            snackbar = Snackbar.make(this.getView(), "Are You sure you want to delete this?",
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("DELETE", v -> RecipeDBHelper.deleteRecipe(recipe, position));
            snackbar.addCallback(new Snackbar.Callback() {

                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                        // IF Snackbar closed on its own, item was NOT deleted
                        rvAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        snackbar.show();
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.recipe_sort_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    static public void stopRecipesFragmentProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

}