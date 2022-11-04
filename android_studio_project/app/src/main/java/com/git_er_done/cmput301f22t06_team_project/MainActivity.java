package com.git_er_done.cmput301f22t06_team_project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.git_er_done.cmput301f22t06_team_project.dbHelpers.FirebaseCallback;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;

import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientsFirebaseCallBack;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipesDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.Recipe;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeIngredient;
import com.git_er_done.cmput301f22t06_team_project.models.RecipeTypes.BreakFastRecipe;
import com.git_er_done.cmput301f22t06_team_project.models.ingredientTypes.FruitIngredient;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        fruit_salad.setIngredientsList(recipeIngredients);
        RecipesDBHelper recipesDBHelper = new RecipesDBHelper();
        recipesDBHelper.addRecipe(fruit_salad);

        recipesDBHelper.setRecipesAdapter();

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_recipes_menu_item:
                    Log.i("MENU_DRAWER_TAG", "Recipes menu selected");
                    navController.navigate(R.id.recipesFragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_ingredients_menu_item:
                    Log.i("MENU_DRAWER_TAG", "Ingredients menu selected");
                    navController.navigate(R.id.ingredientsFragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_shopping_list_menu_item:
                    Log.i("MENU_DRAWER_TAG", "Shopping List menu selected");
                    navController.navigate(R.id.shoppingListFragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_meal_planner_menu_item:
                    Log.i("MENU_DRAWER_TAG", "Meal Planner menu selected");
                    navController.navigate(R.id.mealPlannerFragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}