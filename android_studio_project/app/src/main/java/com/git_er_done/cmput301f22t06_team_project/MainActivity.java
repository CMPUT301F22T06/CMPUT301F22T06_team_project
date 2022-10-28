package com.git_er_done.cmput301f22t06_team_project;

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

import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
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


        navigationView.setNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
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