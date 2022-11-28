package com.git_er_done.cmput301f22t06_team_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.MealDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.RecipeDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.UserDefinedDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    public static ArrayList<Meal> dummyMeals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_CMPUT301F22T06_team_project);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        IngredientDBHelper.getInstance();
        RecipeDBHelper.getInstance();
        MealDBHelper.getInstance();
        UserDefinedDBHelper.getInstance(); // Saheel was here

        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
//                menuInflater.inflate(R.menu.ingredient_sort_menu, menu);
                // Add menu options here

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                // Handle Menu Options Selection Here

                return false;
            }
        });
        //recipe toolbar action
        Toolbar RecipeToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(RecipeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
//                menuInflater.inflate(R.menu.ingredient_sort_menu, menu);
                // Add menu options here

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                // Handle Menu Options Selection Here

                return false;
            }
        });
        //recipe toolbar action
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

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